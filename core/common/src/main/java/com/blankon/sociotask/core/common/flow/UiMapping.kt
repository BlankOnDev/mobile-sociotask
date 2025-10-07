package com.blankon.sociotask.core.common.flow

import com.blankon.sociotask.core.common.utils.state.ApiState
import com.blankon.sociotask.core.common.utils.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T> Flow<ApiState<T>>.asUiState(): Flow<UiState<T>> =
    map { api ->
        when (api) {
            is ApiState.Loading -> UiState.StateLoading
            is ApiState.Success -> UiState.StateSuccess(api.data)
            is ApiState.Error -> UiState.StateFailed(api.throwable)
        }
    }