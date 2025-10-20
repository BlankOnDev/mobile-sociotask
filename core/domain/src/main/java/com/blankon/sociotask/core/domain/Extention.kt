package com.blankon.sociotask.core.domain

inline fun <D, E : DomainError, R> Result<D, E>.map(transform: (D) -> R): Result<R, E> =
    when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Error -> this
    }

inline fun <D, E : DomainError, F : DomainError> Result<D, E>.mapError(transform: (E) -> F): Result<D, F> =
    when (this) {
        is Result.Success -> this
        is Result.Error -> Result.Error(transform(error))
    }

inline fun <D, E : DomainError, R> Result<D, E>.flatMap(transform: (D) -> Result<R, E>): Result<R, E> =
    when (this) {
        is Result.Success -> transform(data)
        is Result.Error -> this
    }

inline fun <D, E : DomainError, T> Result<D, E>.fold(
    onSuccess: (D) -> T,
    onError: (E) -> T
): T = when (this) {
    is Result.Success -> onSuccess(data)
    is Result.Error -> onError(error)
}
