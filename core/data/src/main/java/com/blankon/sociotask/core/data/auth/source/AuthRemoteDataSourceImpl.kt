package com.blankon.sociotask.core.data.auth.source

import com.blankon.sociotask.core.data.model.request.SignInRequest
import com.blankon.sociotask.core.data.source.remote.ApiService
import com.blankon.sociotask.core.domain.Result
import com.blankon.sociotask.core.domain.auth.error.DataError
import com.blankon.sociotask.core.domain.auth.model.RegisteredAccount
import com.blankon.sociotask.core.domain.auth.model.SignInParams
import com.blankon.sociotask.core.domain.auth.model.SignUpParams
import com.blankon.sociotask.core.domain.auth.model.User
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : AuthDataRemoteSource {
    override suspend fun signInWithEmail(signInParams: SignInParams): Result<User, DataError> {
        return try {
            val request = SignInRequest(
                email = signInParams.email,
                password = signInParams.password
            )
            val response = apiService.signInWithEmail(request)

            val token = response.data?.token
            if (response.status == "success" && token != null) {
                val user = User(
                    id = "",
                    email = signInParams.email,
                    token = token
                )
                Result.Success(user)
            } else {
                Result.Error(DataError.Network.Auth.InvalidCredentials)
            }
        } catch (e: HttpException) {
            Result.Error(DataError.Network.ServerError)
        } catch (e: IOException) {
            Result.Error(DataError.Network.NoInternet)
        } catch (e: Exception) {
            Result.Error(DataError.Unknown())
        }
    }

    override suspend fun signUpWithEmail(signUpParams: SignUpParams): Result<RegisteredAccount, DataError> {
        TODO("Not yet implemented")
    }
}