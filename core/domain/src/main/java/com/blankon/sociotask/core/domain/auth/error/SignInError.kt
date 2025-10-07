package com.blankon.sociotask.core.domain.auth.error

import com.blankon.sociotask.core.domain.auth.error.Error


sealed interface SignInError : Error {
    sealed interface Validation : SignInError {
        data object EmailBlank : Validation
        data object PasswordBlank : Validation
    }
    data class Data(val cause: DataError.Network) : SignInError
}