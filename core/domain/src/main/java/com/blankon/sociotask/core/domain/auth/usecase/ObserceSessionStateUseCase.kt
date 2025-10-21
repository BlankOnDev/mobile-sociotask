package com.blankon.sociotask.core.domain.auth.usecase

import com.blankon.sociotask.core.domain.auth.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

sealed interface SessionState {
    data object Loading : SessionState
    data object Unauthenticated : SessionState
    data class Authenticated(val token: String) : SessionState
}

class ObserveSessionStateUseCase @Inject constructor(
    private val repo: SessionRepository
) {
    operator fun invoke(): Flow<SessionState> =
        repo.observeAccessToken()
            .map { token ->
                if (token.isNullOrBlank()) SessionState.Unauthenticated
                else SessionState.Authenticated(token)
            }
            .onStart { emit(SessionState.Loading) }
            .distinctUntilChanged()
}
