package com.blankon.sociotask.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankon.sociotask.core.domain.Result
import com.blankon.sociotask.core.domain.auth.error.DataError
import com.blankon.sociotask.core.domain.auth.error.SignInError
import com.blankon.sociotask.core.domain.auth.model.User
import com.blankon.sociotask.core.domain.auth.usecase.SignInWithEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null
) {
    val isFormValid: Boolean get() = email.isNotBlank() && password.isNotBlank()
}

sealed interface SignInIntent {
    data class EmailChanged(val email: String) : SignInIntent
    data class PasswordChanged(val password: String) : SignInIntent
    data object TogglePassword : SignInIntent
    data object Submit : SignInIntent
}

sealed interface SignInEvent {
    data class ShowMessage(val message: String) : SignInEvent
    data class NavigateHome(val userId: String) : SignInEvent
}

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInWithEmailUseCase: SignInWithEmailUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SignInUiState())
    val state: StateFlow<SignInUiState> = _state.asStateFlow()

    private val _events = Channel<SignInEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()


    fun onIntent(intent: SignInIntent) {
        when (intent) {
            is SignInIntent.EmailChanged -> _state.update {
                it.copy(
                    email = intent.email,
                    emailError = null
                )
            }

            is SignInIntent.PasswordChanged -> _state.update {
                it.copy(
                    password = intent.password,
                    passwordError = null
                )
            }

            SignInIntent.TogglePassword -> _state.update { it.copy(showPassword = !it.showPassword) }
            SignInIntent.Submit -> submit()
        }
    }

    private fun submit() {
        val s = _state.value
        if (s.isLoading) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, emailError = null, passwordError = null) }

            when (val res: Result<User, SignInError> = signInWithEmailUseCase(s.email, s.password)) {
                is Result.Success -> {
                    _events.send(SignInEvent.ShowMessage("Login berhasil"))
                    _events.send(SignInEvent.NavigateHome(res.data.id))
                }

                is Result.Error -> handleError(res.error)
            }

            _state.update { it.copy(isLoading = false) }
        }
    }

    private suspend fun handleError(e: SignInError) {
        when (e) {
            is SignInError.Validation.EmailBlank -> _state.update { it.copy(emailError = "Email tidak boleh kosong") }
            is SignInError.Validation.PasswordBlank -> _state.update { it.copy(passwordError = "Password tidak boleh kosong") }
            is SignInError.Data -> _events.send(SignInEvent.ShowMessage(networkErrorMessage(e.cause)))
        }
    }

    private fun networkErrorMessage(err: DataError.Network): String = when (err) {
        DataError.Network.NO_INTERNET -> "Tidak ada koneksi internet"
        DataError.Network.REQUEST_TIMEOUT -> "Permintaan timeout"
        DataError.Network.TOO_MANY_REQUEST -> "Terlalu banyak permintaan"
        DataError.Network.SERVER_ERROR -> "Terjadi kesalahan server"
        DataError.Network.UNKNOWN -> "Kesalahan tidak diketahui"
    }
}