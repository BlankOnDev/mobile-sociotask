package com.blankon.sociotask.core.domain.auth.repository

import com.blankon.sociotask.core.domain.Result
import com.blankon.sociotask.core.domain.auth.error.DataError
import com.blankon.sociotask.core.domain.auth.error.SocialAuthError
import com.blankon.sociotask.core.domain.auth.model.SignInParams
import com.blankon.sociotask.core.domain.auth.model.SignUpParams
import com.blankon.sociotask.core.domain.auth.model.User

interface AuthRepository {
    suspend fun signInWithEmail(credential: SignInParams): Result<User, DataError.Network>
    suspend fun signUpWithEmail(signUpParams: SignUpParams): Result<User, DataError.Network>
    suspend fun authenticateWithGoogle(idToken: String): Result<User, SocialAuthError>

}