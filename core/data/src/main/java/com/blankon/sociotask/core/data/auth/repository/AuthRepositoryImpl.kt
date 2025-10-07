package com.blankon.sociotask.core.data.auth.repository

import com.blankon.sociotask.core.data.auth.source.AuthDataRemoteSource
import com.blankon.sociotask.core.data.auth.source.GoogleAuthDataSource
import com.blankon.sociotask.core.domain.Result
import com.blankon.sociotask.core.domain.auth.error.DataError
import com.blankon.sociotask.core.domain.auth.error.SocialAuthError
import com.blankon.sociotask.core.domain.auth.model.SignInParams
import com.blankon.sociotask.core.domain.auth.model.SignUpParams
import com.blankon.sociotask.core.domain.auth.model.User
import com.blankon.sociotask.core.domain.auth.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remote: AuthDataRemoteSource,
    private val googleDs: GoogleAuthDataSource
) : AuthRepository {

    private fun mapToSocialAuthError(t: Throwable): SocialAuthError {
        val msg = t.message.orEmpty()

        // Contoh mapping; sesuaikan dgn stack-mu (Firebase/Backend)
        return when {
            // Provider-level
            msg.contains("INVALID_ID_TOKEN", true) ||
                    msg.contains(
                        "CREDENTIAL_MISMATCH",
                        true
                    ) -> SocialAuthError.Provider.InvalidCredential

            msg.contains("ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL", true) ||
                    msg.contains(
                        "NEEDS_ACCOUNT_LINKING",
                        true
                    ) -> SocialAuthError.Provider.AccountNeedsLinking

            // Network-level → bungkus ke DataError.Network
            msg.contains("TIMEOUT", true) -> SocialAuthError.Data(DataError.Network.REQUEST_TIMEOUT)
            msg.contains("429", true) -> SocialAuthError.Data(DataError.Network.TOO_MANY_REQUEST)
            msg.contains("NO_INTERNET", true) -> SocialAuthError.Data(DataError.Network.NO_INTERNET)
            msg.contains("5xx", true) -> SocialAuthError.Data(DataError.Network.SERVER_ERROR)

            else -> SocialAuthError.Unknown
        }
    }

    override suspend fun signInWithEmail(
        signInParams: SignInParams
    ): Result<User, DataError.Network> = try {
        Result.Success(remote.signIn(signInParams))
    } catch (t: Throwable) {
        Result.Error(mapNetwork(t))
    }

    override suspend fun signUpWithEmail(
        signUpParams: SignUpParams
    ): Result<User, DataError.Network> {
//        return try {
//            val user = remote.signIn(signUpParams)
//            Result.Success(user)
//
//        } catch (t: Throwable) {
//            Result.Error(mapNetwork(t))
//        }
    }

    private fun mapNetwork(t: Throwable): DataError.Network = when {
        t.message?.contains("TIMEOUT", true) == true -> DataError.Network.REQUEST_TIMEOUT
        t.message?.contains("429", true) == true -> DataError.Network.TOO_MANY_REQUEST
        t.message?.contains("NO_INTERNET", true) == true -> DataError.Network.NO_INTERNET
        t.message?.startsWith("5") == true -> DataError.Network.SERVER_ERROR
        else -> DataError.Network.UNKNOWN
    }

    override suspend fun authenticateWithGoogle(idToken: String): Result<User, SocialAuthError> {
        return try {
            val user = googleDs.signInWithGoogle(idToken)
            Result.Success(user)
        } catch (t: Throwable) {
            Result.Error(mapToSocialAuthError(t))
        }
    }
}