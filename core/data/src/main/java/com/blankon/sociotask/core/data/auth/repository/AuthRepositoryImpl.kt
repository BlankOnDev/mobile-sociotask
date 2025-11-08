package com.blankon.sociotask.core.data.auth.repository

import com.blankon.sociotask.core.data.auth.source.AuthDataRemoteSource
import com.blankon.sociotask.core.domain.utils.Result
import com.blankon.sociotask.core.domain.error.DataError
import com.blankon.sociotask.core.domain.error.SocialAuthDomainError
import com.blankon.sociotask.core.domain.model.RegisteredAccount
import com.blankon.sociotask.core.domain.model.SignInParams
import com.blankon.sociotask.core.domain.model.SignUpParams
import com.blankon.sociotask.core.domain.model.User
import com.blankon.sociotask.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remote: AuthDataRemoteSource,
//    private val googleDs: GoogleAuthDataSource
) : AuthRepository {

    override suspend fun signUpWithEmail(
        signUpParams: SignUpParams
    ): Result<RegisteredAccount, DataError> =
        remote.signUpWithEmail(signUpParams)


    override suspend fun signInWithEmail(signInParams: SignInParams): Result<User, DataError> =
        remote.signInWithEmail(signInParams)
}

//    override suspend fun authenticateWithGoogle(idToken: String): Result<User, SocialAuthDomainError> =
//        try {
//            val user = googleDs.signInWithGoogle(idToken)
//            Result.Success(user)
//        } catch (t: Throwable) {
//            Result.Error(mapToSocialAuthError(t))
//        }

private fun mapToSocialAuthError(t: Throwable): SocialAuthDomainError {
    val msg = t.message.orEmpty()
    return when {
        // contoh provider-level; sesuaikan dengan stack (Firebase, OneTap, dst.)
        msg.contains(
            "INVALID_ID_TOKEN",
            true
        ) -> SocialAuthDomainError.Provider.InvalidCredential

        msg.contains(
            "CREDENTIAL_MISMATCH",
            true
        ) -> SocialAuthDomainError.Provider.InvalidCredential

        msg.contains("ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL", true) ->
            SocialAuthDomainError.Provider.AccountNeedsLinking

        // jika kamu ingin bungkus network ke dalam SocialAuthDomainError.Data(DataError)
        msg.contains("TIMEOUT", true) -> SocialAuthDomainError.Data(DataError.Network.Timeout)
        msg.contains(
            "429",
            true
        ) -> SocialAuthDomainError.Data(DataError.Network.TooManyRequests)

        msg.contains(
            "NO_INTERNET",
            true
        ) -> SocialAuthDomainError.Data(DataError.Network.NoInternet)

        else -> SocialAuthDomainError.Unknown
    }

}
