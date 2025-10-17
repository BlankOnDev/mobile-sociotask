package com.blankon.sociotask.core.data.source.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "app_data_store")

class AppDataStore @Inject constructor(context: Context) {
    private val ds = context.dataStore

    private object Keys {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
    }

    val authTokenFlow: Flow<String?> = ds.data.map { it[Keys.AUTH_TOKEN] }

    suspend fun getAuthTokenOnce(): String? =
        ds.data.map { it[Keys.AUTH_TOKEN] }.firstOrNull()

    suspend fun setAuthToken(token: String?) {
        ds.edit { prefs ->
            if (token.isNullOrBlank()) prefs.remove(Keys.AUTH_TOKEN)
            else prefs[Keys.AUTH_TOKEN] = token
        }
    }

    suspend fun clearAll() {
        ds.edit { it.clear() }
    }
}