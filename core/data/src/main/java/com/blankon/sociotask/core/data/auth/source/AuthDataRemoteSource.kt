package com.blankon.sociotask.core.data.auth.source

import com.blankon.sociotask.core.domain.utils.Result
import com.blankon.sociotask.core.domain.error.DataError
import com.blankon.sociotask.core.domain.model.RegisteredAccount
import com.blankon.sociotask.core.domain.model.SignInParams
import com.blankon.sociotask.core.domain.model.SignUpParams
import com.blankon.sociotask.core.domain.model.User

interface AuthDataRemoteSource {
    suspend fun signInWithEmail(signInParams: SignInParams): Result<User, DataError>
    suspend fun signUpWithEmail(signUpParams: SignUpParams): Result<RegisteredAccount, DataError>
}