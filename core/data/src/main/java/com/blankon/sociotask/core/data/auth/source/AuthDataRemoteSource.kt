package com.blankon.sociotask.core.data.auth.source

import com.blankon.sociotask.core.domain.Result
import com.blankon.sociotask.core.domain.auth.error.DataError
import com.blankon.sociotask.core.domain.auth.model.RegisteredAccount
import com.blankon.sociotask.core.domain.auth.model.SignInParams
import com.blankon.sociotask.core.domain.auth.model.SignUpParams
import com.blankon.sociotask.core.domain.auth.model.User

interface AuthDataRemoteSource {
    suspend fun signInWithEmail(signInParams: SignInParams): Result<User, DataError>
    suspend fun signUpWithEmail(signUpParams: SignUpParams): Result<RegisteredAccount, DataError>
}