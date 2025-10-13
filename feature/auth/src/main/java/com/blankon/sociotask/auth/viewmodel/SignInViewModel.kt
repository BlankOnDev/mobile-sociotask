package com.blankon.sociotask.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankon.sociotask.core.domain.Result
import com.blankon.sociotask.core.domain.auth.error.SignInError
import com.blankon.sociotask.core.domain.auth.model.User
import com.blankon.sociotask.core.domain.auth.usecase.SignInWithEmailUseCase
import com.blankon.sociotask.feature.auth.R
import com.blankon.sosiotask.core.ui.UiText
import com.blankon.sosiotask.core.ui.toUiText
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
    val emailError: UiText? = null,
    val passwordError: UiText? = null
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
    data class ShowMessage(val message: UiText) : SignInEvent
    data class NavigateHome(val userId: String) : SignInEvent
}

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInWithEmailUseCase: SignInWithEmailUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private val _events = Channel<SignInEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onIntent(intent: SignInIntent) {
        when (intent) {
            is SignInIntent.EmailChanged -> _uiState.update {
                it.copy(email = intent.email, emailError = null)
            }

            is SignInIntent.PasswordChanged -> _uiState.update {
                it.copy(password = intent.password, passwordError = null)
            }

            is SignInIntent.TogglePassword -> _uiState.update { it.copy(showPassword = !it.showPassword) }
            is SignInIntent.Submit -> submit()
        }
    }

    private fun submit() {
        val s = _uiState.value
        if (s.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, emailError = null, passwordError = null) }
            try {
                when (val res: Result<User, SignInError> =
                    signInWithEmailUseCase(s.email, s.password)) {
                    is Result.Success -> {
                        _events.send(SignInEvent.ShowMessage(UiText.StringResource(R.string.login_success)))
                        _events.send(SignInEvent.NavigateHome(res.data.id))
                    }

                    is Result.Error -> handleError(res.error)
                }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private suspend fun handleError(e: SignInError) {
        when (e) {
            is SignInError.Validation.EmailBlank -> _uiState.update {
                it.copy(emailError = e.toUiText())
            }

            is SignInError.Validation.PasswordBlank -> _uiState.update {
                it.copy(passwordError = e.toUiText())
            }

            is SignInError.Data ->
                _events.send(SignInEvent.ShowMessage(e.cause.toUiText()))
        }
    }
}