package com.blankon.sociotask.core.domain.error

import com.blankon.sociotask.core.domain.utils.DomainError

sealed interface SignUpError : DomainError {
    sealed interface Validation : SignUpError {
        data object EmailBlank : Validation
        data object PasswordBlank : Validation
        data object UsernameBlank : Validation
        data object FullNameBlank : Validation
    }

    data class Data(val cause: DataError) : SignUpError
}