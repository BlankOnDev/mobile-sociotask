package com.blankon.sociotask.core.data.auth.source

import com.blankon.sociotask.core.domain.Result
import com.blankon.sociotask.core.domain.auth.error.DataError
import com.blankon.sociotask.core.domain.auth.model.SignInParams
import com.blankon.sociotask.core.domain.auth.model.User

interface AuthDataRemoteSource{
    suspend fun signIn(signInParams: SignInParams) : Result<User, DataError.Network>
}