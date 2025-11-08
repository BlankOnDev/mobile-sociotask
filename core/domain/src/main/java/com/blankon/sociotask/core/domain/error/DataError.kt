package com.blankon.sociotask.core.domain.error

import com.blankon.sociotask.core.domain.utils.DomainError

sealed interface DataError : DomainError {
    sealed interface Network : DataError {
        data object NoInternet : Network
        data object Timeout : Network
        data object TooManyRequests : Network
        data object ServerError : Network
        data class Http(
            val code: Int,
            val message: String? = null,
            val bodySnippet: String? = null
        ) : Network

        data object Unreachable : Network

        sealed interface Auth : DataError{
            data object InvalidCredentials : Auth
            data object EmailNotVerified : Auth
            data object Unauthorized : Auth
        }
    }

    data class Unknown(val cause: Throwable? = null) : DataError

//    sealed interface Local : DataError
}