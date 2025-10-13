package com.blankon.sociotask.core.domain.auth.error

import com.blankon.sociotask.core.domain.DomainError

sealed interface SignUpError : DomainError {
    sealed interface Validation : SignUpError {
        data object EmailBlank : Validation
        data object PasswordBlank : Validation
        data object UsernameBlank : Validation
        data object FullNameBlank : Validation
    }

    data class Data(val cause: DataError) : SignUpError
}