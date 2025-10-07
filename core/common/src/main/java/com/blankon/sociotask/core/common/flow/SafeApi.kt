package com.blankon.sociotask.core.common.flow

import com.blankon.sociotask.core.common.utils.state.ApiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

inline fun <T, R> safeApiFlow(
    crossinline fetchApi: suspend () -> T,
    crossinline transformData: (T) -> R
): Flow<ApiState<R>> = flow {
    emit(ApiState.Loading)
    try {
        val t = fetchApi()
        emit(ApiState.Success(transformData(t)))
    } catch (ce: CancellationException) {
        throw ce
    } catch (e: Throwable) {
        emit(ApiState.Error(e))
    }
}.flowOn(Dispatchers.IO)