package com.blankon.sociotask.core.domain.auth.repository

import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    suspend fun saveAccessToken(token: String)
    suspend fun clear()
    fun observeAccessToken(): Flow<String?>
    suspend fun getAccessToken(): String?
}