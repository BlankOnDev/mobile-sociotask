package com.blankon.sociotask.core.domain.auth.error

import com.blankon.sociotask.core.domain.DomainError

sealed interface SignInError : DomainError {
    sealed interface Validation : SignInError {
        data object EmailBlank : Validation
        data object PasswordBlank : Validation
    }

    data class Data(val cause: DataError) : SignInError
}