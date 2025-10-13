package com.blankon.sociotask.core.domain

typealias RootError = DomainError

sealed interface Result<out D, out E : RootError> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : RootError>(val error: E) : Result<Nothing, E>
}