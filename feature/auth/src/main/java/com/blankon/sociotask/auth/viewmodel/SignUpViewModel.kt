package com.blankon.sociotask.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankon.sociotask.core.domain.Result
import com.blankon.sociotask.core.domain.auth.error.SignUpError
import com.blankon.sociotask.core.domain.auth.model.RegisteredAccount
import com.blankon.sociotask.core.domain.auth.model.SignUpParams
import com.blankon.sociotask.core.domain.auth.usecase.AuthenticateWithGoogleUseCase
import com.blankon.sociotask.core.domain.auth.usecase.SignUpWithEmailUseCase
import com.blankon.sociotask.feature.auth.R
import com.blankon.sosiotask.core.ui.UiText
import com.blankon.sosiotask.core.ui.toUiText
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
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
    val confirmPasswordError: UiText? = null,
    val usernameError: UiText? = null,
    val fullNameError: UiText? = null
) {
    val isFormValid: Boolean
        get() = username.isNotBlank()
                && fullName.isNotBlank()
                && email.isNotBlank()
                && password.isNotBlank()
                && confirmPassword.isNotBlank()
}

sealed interface SignUpIntent {
    data class FullNameChanged(val fullName: String) : SignUpIntent
    data class UsernameChanged(val username: String) : SignUpIntent
    data class EmailChanged(val email: String) : SignUpIntent
    data class PasswordChanged(val password: String) : SignUpIntent
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignUpIntent
    data object TogglePassword : SignUpIntent
    data object ToggleConfirmPassword : SignUpIntent
    data object Submit : SignUpIntent
    data class SubmitGoogle(val idToken: String) : SignUpIntent

//    data class SubmitTwitter(
//        val oauthToken: String,
//        val oauthTokenSecret: String,
//    ) : SignUpEvent
}


sealed interface SignUpEvent {
    data class ShowMessage(val message: UiText) : SignUpEvent
    data object NavigateSignIn : SignUpEvent

//    data object RequestGoogleSignIn : SignUpEvent
//    data object RequestTwitterSignIn : SignUpEvent
}

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase,
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
                    it.copy(email = intent.email, emailError = null)
                }

            is SignUpIntent.PasswordChanged ->
                _uiState.update {
                    it.copy(
                        password = intent.password,
                        passwordError = null,
                        confirmPasswordError = null
                    )
                }

            is SignUpIntent.ConfirmPasswordChanged ->
                _uiState.update {
                    it.copy(
                        confirmPassword = intent.confirmPassword,
                        confirmPasswordError = null
                    )
                }

            is SignUpIntent.TogglePassword ->
                _uiState.update { it.copy(showPassword = !it.showPassword) }

            is SignUpIntent.ToggleConfirmPassword ->
                _uiState.update { it.copy(showConfirmPassword = !it.showConfirmPassword) }

            is SignUpIntent.Submit -> submit()

            is SignUpIntent.SubmitGoogle -> {}

//            is SignUpIntent.SubmitTwitter -> submitTwitter(
//                intent.oauthToken,
//                intent.oauthTokenSecret
//            )
        }
    }

    private fun submit() {
        val state = _uiState.value
        if (state.isLoading) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    emailError = null,
                    passwordError = null,
                    confirmPasswordError = null
                )
            }

            when (val res: Result<RegisteredAccount, SignUpError> =
                signUpWithEmailUseCase(
                    params = SignUpParams(
                        email = state.email,
                        password = state.password,
                        fullName = state.fullName,
                        username = state.username
                    )
                )) {
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    handleError(res.error)
                }

                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _events.send(SignUpEvent.ShowMessage(UiText.StringResource(R.string.signup_success)))
                    _events.send(SignUpEvent.NavigateSignIn)
                }
            }
        }
    }


//    private fun submitGoogle(idToken: String) {
//        viewModelScope.launch {
//            signInWithGoogle(idToken).collect { result ->
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
//                                result.throwable.message ?: "Login Google gagal"
//                            )
//                        )
//                    }
//                }
//            }
//        }
//    }

    private suspend fun handleError(e: SignUpError) {
        when (e) {
            is SignUpError.Validation.EmailBlank -> _uiState.update {
                it.copy(emailError = e.toUiText())
            }

            is SignUpError.Validation.FullNameBlank -> _uiState.update {
                it.copy(
                    fullNameError = e.toUiText()
                )
            }

            is SignUpError.Validation.PasswordBlank -> _uiState.update {
                it.copy(
                    passwordError = e.toUiText()
                )
            }

            is SignUpError.Validation.UsernameBlank -> _uiState.update {
                it.copy(
                    usernameError = e.toUiText()
                )
            }

            is SignUpError.Data -> _events.send(SignUpEvent.ShowMessage(e.cause.toUiText()))
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
}
