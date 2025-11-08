package com.blankon.sociotask.core.domain.error

import com.blankon.sociotask.core.domain.utils.DomainError

sealed interface SignInError : DomainError {
    sealed interface Validation : SignInError {
        data object EmailBlank : Validation
        data object PasswordBlank : Validation
    }

    data class Data(val cause: DataError) : SignInError
}