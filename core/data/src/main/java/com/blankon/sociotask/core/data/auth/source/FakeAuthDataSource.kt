package com.blankon.sociotask.core.data.auth.source

import com.blankon.sociotask.core.domain.Result
import com.blankon.sociotask.core.domain.auth.error.DataError
import com.blankon.sociotask.core.domain.auth.model.SignInParams
import com.blankon.sociotask.core.domain.auth.model.SignUpParams
import com.blankon.sociotask.core.domain.auth.model.User
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeAuthDataSource @Inject constructor() : AuthDataRemoteSource {
    override suspend fun signInWithEmail(signInParams: SignInParams): Result<User, DataError> {
        delay(600)
        val email = signInParams.email.trim()
        val password = signInParams.password

        return if (email == "mramadaaditya@gmail.com" && password == "madara123") {
            Result.Success(User(id = "1", email = email, token = "token-demo"))
        } else {
            Result.Error(DataError.Network.Auth.InvalidCredentials)
        }
    }

    override suspend fun signUpWithEmail(signUpParams: SignUpParams): Result<User, DataError> {
        delay(600)
        val email = signUpParams.email.trim()
        val password = signUpParams.password
        val username = signUpParams.username.trim()
        val fullName = signUpParams.fullName.trim()
        return if (email == "mramadaaditya@gmail.com" && password == "madara123" && username == "madara") {
            Result.Success(User(id = "2", email = signUpParams.email, token = "token-demo-signup"))
        } else {
            Result.Error(DataError.Network.Auth.InvalidCredentials)
        }
    }
}