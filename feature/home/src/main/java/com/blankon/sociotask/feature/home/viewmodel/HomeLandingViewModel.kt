package com.blankon.sociotask.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankon.sociotask.core.data.source.local.AppDataStore
import com.blankon.sociotask.core.domain.auth.repository.SessionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class HomeLandingViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel() {
    val token: StateFlow<String?> = sessionRepository
        .observeAccessToken()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    suspend fun logout () {
        sessionRepository.clear()
    }

}