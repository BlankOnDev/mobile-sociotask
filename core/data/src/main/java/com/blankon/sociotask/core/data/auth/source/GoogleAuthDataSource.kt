package com.blankon.sociotask.core.data.auth.source

import com.blankon.sociotask.core.domain.auth.model.User

interface GoogleAuthDataSource {
    suspend fun signInWithGoogle(idToken : String) : User
}