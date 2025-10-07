package com.blankon.sociotask.core.domain.auth.error

sealed interface SocialAuthError : Error {
    sealed interface Validation : SocialAuthError {
        data object TokenBlank : Validation
    }

    sealed interface Provider : SocialAuthError {
        data object InvalidCredential : Provider
        data object AccountNeedsLinking : Provider
    }

    data class Data(val cause: DataError.Network) : SocialAuthError
    data object Unknown : SocialAuthError
}

