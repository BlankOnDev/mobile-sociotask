package com.blankon.sociotask.core.domain.auth.error

import com.blankon.sociotask.core.domain.auth.error.Error

sealed interface DataError : Error {
    enum class Network : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUEST,
        NO_INTERNET,
        SERVER_ERROR,
        UNKNOWN
    }

    enum class Local : DataError {
        UNKNOWN
    }
}