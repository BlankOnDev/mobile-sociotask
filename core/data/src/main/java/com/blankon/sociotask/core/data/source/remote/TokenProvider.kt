package com.blankon.sociotask.core.data.source.remote

import com.blankon.sociotask.core.data.source.local.AppDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.atomics.ExperimentalAtomicApi

interface TokenProvider {
    fun current(): String?
    suspend fun set(token: String?)
    suspend fun clear()
}

@OptIn(ExperimentalAtomicApi::class)
@Singleton
class TokenProviderImpl @Inject constructor(
    private val store: AppDataStore
) : TokenProvider {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val cache = AtomicReference<String?>(null)

    init {
        store.authTokenFlow
            .onEach { cache.set(it) }
            .launchIn(scope)
    }

    override fun current(): String? = cache.get()

    override suspend fun set(token: String?) {
        store.setAuthToken(token)
    }

    override suspend fun clear() {
        store.setAuthToken(null)
    }
}