package com.blankon.sociotask.core.data.utils

import com.blankon.sociotask.core.data.model.response.ApiResponse
import com.blankon.sociotask.core.domain.Result
import com.blankon.sociotask.core.domain.auth.error.DataError
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException


private suspend inline fun <T> safeResult(

    crossinline call: suspend () -> Response<ApiResponse<T>>

): Result<ApiResponse<T>, DataError> = try {

    val resp = call()

    if (resp.isSuccessful) {
        val body = resp.body()
        if (body?.data != null) Result.Success(body)
        else {
            Result.Error(
                DataError.Network.Http(
                    code = resp.code(),
                    message = "Empty response body or null data"
                )
            )
        }
    } else {
        val errorBodySnippet = resp.errorBody()?.string()?.take(200)
        when (val code = resp.code()) {
            401 -> Result.Error(DataError.Network.Auth.Unauthorized)
            429 -> Result.Error(DataError.Network.TooManyRequests)
            in 500..599 -> Result.Error(DataError.Network.ServerError)
            else -> Result.Error(
                DataError.Network.Http(
                    code = code,
                    message = resp.message(),
                    bodySnippet = errorBodySnippet
                )
            )
        }
    }
} catch (e: SocketTimeoutException) {
    Result.Error(DataError.Network.Timeout)
} catch (e: IOException) {
    Result.Error(DataError.Network.NoInternet)
} catch (e: Throwable) {
    Result.Error(DataError.Unknown(e))
}
