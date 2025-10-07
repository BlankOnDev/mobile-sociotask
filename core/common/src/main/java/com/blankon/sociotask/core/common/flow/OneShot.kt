package com.blankon.sociotask.core.common.flow

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class OneShotEventBus<T> {
    private val _events = MutableSharedFlow<T>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<T> = _events

    fun emit(value: T) {
        _events.tryEmit(value)
    }

}