package com.blankon.sociotask.core.data.auth.repository

import com.blankon.sociotask.core.data.source.local.AppDataStore
import com.blankon.sociotask.core.domain.auth.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val dataStore: AppDataStore
) : SessionRepository {
    override suspend fun saveAccessToken(token: String) {
        dataStore.setAuthToken(token)
    }

    override suspend fun clear() {
        dataStore.clearAll()
    }

    override fun observeAccessToken(): Flow<String?> = dataStore.authTokenFlow


    override suspend fun getAccessToken(): String? = dataStore.getAuthTokenOnce()

}