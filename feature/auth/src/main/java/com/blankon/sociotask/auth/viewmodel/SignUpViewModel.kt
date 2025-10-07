package com.blankon.sociotask.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankon.sociotask.core.domain.Result
import com.blankon.sociotask.core.domain.auth.usecase.AuthenticateWithGoogleUseCase
import com.blankon.sociotask.core.domain.auth.usecase.SignUpWithEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignUpUiState(
    val username: String = "",
    val fullName: String = "",
    val email: String = "",
    val confirmPassword: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val showConfirmPassword: Boolean = false,
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,

    ) {
    val isFormValid: Boolean get() = email.isNotEmpty() && password == confirmPassword
    val isPasswordMatch: Boolean
        get() = email.isNotBlank() &&
                password.isNotBlank() &&
                fullName.isNotBlank() &&
                username.isNotBlank() &&
                confirmPassword.isNotBlank() &&
                emailError == null &&
                passwordError == null &&
                confirmPasswordError == null

}

sealed interface SignUpIntent {
    data class FullNameChanged(val fullName: String) : SignUpIntent
    data class UsernameChanged(val username: String) : SignUpIntent
    data class EmailChanged(val email: String) : SignUpIntent
    data class PasswordChanged(val password: String) : SignUpIntent
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignUpIntent

    data object TogglePassword : SignUpEvent
    data object ToggleConfirmPassword : SignUpEvent

    data object Submit : SignUpEvent

    data class SubmitGoogle(val idToken: String) : SignUpEvent
//    data class SubmitTwitter(
//        val oauthToken: String,
//        val oauthTokenSecret: String,
//    ) : SignUpEvent
}


sealed interface SignUpEvent {
    data class ShowMessage(val message: String) : SignUpEvent
    data class NavigateHome(val userId: String) : SignUpEvent

//    data object RequestGoogleSignIn : SignUpEvent
//    data object RequestTwitterSignIn : SignUpEvent
}

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpWithEmail: SignUpWithEmailUseCase,
    private val signInWithGoogle: AuthenticateWithGoogleUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    private val _events = Channel<SignUpEvent>(Channel.BUFFERED)
    val events: Flow<SignUpEvent> = _events.receiveAsFlow()

    fun onIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.FullNameChanged ->
                _uiState.update { it.copy(fullName = intent.fullName) }

            is SignUpIntent.UsernameChanged ->
                _uiState.update { it.copy(username = intent.username) }

            is SignUpIntent.EmailChanged ->
                _uiState.update {
                    it.copy(
                        email = intent.email,
                        emailError = validateEmail(intent.email)
                    )
                }

            is SignUpIntent.PasswordChanged ->
                _uiState.update {
                    val pwdErr = validatePassword(intent.password)
                    val confErr = validateConfirmPassword(intent.password, it.confirmPassword)
                    it.copy(
                        password = intent.password,
                        passwordError = pwdErr,
                        confirmPasswordError = confErr
                    )
                }

            is SignUpIntent.ConfirmPasswordChanged ->
                _uiState.update {
                    val confErr = validateConfirmPassword(it.password, intent.confirm)
                    it.copy(
                        confirmPassword = intent.confirmPassword,
                        confirmPasswordError = confErr
                    )
                }

            is SignUpIntent.TogglePassword ->
                _uiState.update { it.copy(showPassword = !it.showPassword) }

            is SignUpIntent.ToggleConfirmPassword ->
                _uiState.update { it.copy(showConfirmPassword = !it.showConfirmPassword) }

            is SignUpIntent.Submit -> submitEmail()

            is SignUpIntent.SubmitGoogle -> submitGoogle(intent.idToken)

//            is SignUpIntent.SubmitTwitter -> submitTwitter(
//                intent.oauthToken,
//                intent.oauthTokenSecret
//            )
        }
    }

    private fun submitEmail() {
        val state = _uiState.value
        // Final guard
        val emailError = validateEmail(state.email)
        val passwordError = validatePassword(state.password)
        val confirmError = validateConfirmPassword(state.password, state.confirmPassword)

        _uiState.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError,
                confirmPasswordError = confirmError
            )
        }
        if (emailError != null || passwordError != null || confirmError != null) return

        viewModelScope.launch {
            signUpWithEmail(
                email = state.email,
                password = state.password,           // hanya kirim password utama
                fullName = state.fullName.ifBlank { null },
                username = state.username.ifBlank { null }
            ).collect { result ->
                when (result) {
                    is Result.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Result.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _events.send(SignUpEvent.NavigateHome(result.data.id))
                    }

                    is Result.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _events.send(
                            SignUpEvent.ShowMessage(
                                result.throwable.message ?: "Gagal mendaftar"
                            )
                        )
                    }
                }
            }
        }
    }

    private fun submitGoogle(idToken: String) {
        viewModelScope.launch {
            signInWithGoogle(idToken).collect { result ->
                when (result) {
                    is Result.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Result.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _events.send(SignUpEvent.NavigateHome(result.data.id))
                    }

                    is Result.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _events.send(
                            SignUpEvent.ShowMessage(
                                result.throwable.message ?: "Login Google gagal"
                            )
                        )
                    }
                }
            }
        }
    }

//    private fun submitTwitter(oauthToken: String, oauthTokenSecret: String) {
//        viewModelScope.launch {
//            signInWithTwitter(oauthToken, oauthTokenSecret).collect { result ->
//                when (result) {
//                    is Result.Loading -> _uiState.update { it.copy(isLoading = true) }
//                    is Result.Success -> {
//                        _uiState.update { it.copy(isLoading = false) }
//                        _events.send(SignUpEvent.NavigateHome(result.data.id))
//                    }
//
//                    is Result.Error -> {
//                        _uiState.update { it.copy(isLoading = false) }
//                        _events.send(
//                            SignUpEvent.ShowMessage(
//                                result.throwable.message ?: "Login Twitter gagal"
//                            )
//                        )
//                    }
//                }
//            }
//        }
//    }

    // --- Simple validators (bisa kamu ganti ke UiText/Resource) ---
    private fun validateEmail(email: String): String? =
        if (email.isBlank()) "Email tidak boleh kosong"
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            "Format email tidak valid" else null

    private fun validatePassword(password: String): String? =
        when {
            password.isBlank() -> "Password tidak boleh kosong"
            password.length < 8 -> "Minimal 8 karakter"
            else -> null
        }

    private fun validateConfirmPassword(password: String, confirm: String): String? =
        when {
            confirm.isBlank() -> "Konfirmasi password tidak boleh kosong"
            password != confirm -> "Password tidak sama"
            else -> null
        }

}
