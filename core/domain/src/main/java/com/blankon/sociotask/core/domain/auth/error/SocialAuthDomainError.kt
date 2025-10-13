package com.blankon.sociotask.core.domain.auth.error

import com.blankon.sociotask.core.domain.DomainError

sealed interface SocialAuthDomainError : DomainError {
    sealed interface Validation : SocialAuthDomainError {
        data object TokenBlank : Validation
    }

    sealed interface Provider : SocialAuthDomainError {
        data object InvalidCredential : Provider
        data object AccountNeedsLinking : Provider
    }

    data class Data(val cause: DataError.Network) : SocialAuthDomainError
    data object Unknown : SocialAuthDomainError
}

