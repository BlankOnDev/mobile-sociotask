package com.blankon.sociotask.core.ui

import androidx.compose.material3.SnackbarDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

data class SnackbarEvent(
    val message: String,
    val action: SnackbarAction? = null,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val dismissCurrent: Boolean = true,
    // buat tandain ini snackbar dari user / dari sistem (network, error global, dsb)
    val kind: SnackbarKind = SnackbarKind.User,
)

data class SnackbarAction(
    val name: String,
    val action: suspend () -> Unit
)

enum class SnackbarKind {
    User,   // dari UI / ViewModel
    System, // dari network monitor, force logout, dsb
}

object SnackbarController {

    // pakai buffer lebih gede dikit biar event nggak gampang ilang
    private val _events = MutableSharedFlow<SnackbarEvent>(
        replay = 0,
        extraBufferCapacity = 8
    )
    val events = _events.asSharedFlow()

    /**
     * Dipakai dari tempat yang SUSPEND (LaunchedEffect, interactor, dsb)
     */
    suspend fun send(event: SnackbarEvent) {
        _events.emit(event)
    }

    /**
     * Dipakai dari ViewModel / tempat yang nggak punya suspend scope.
     * Caller wajib kirim scope-nya.
     */
    fun send(
        scope: CoroutineScope,
        event: SnackbarEvent
    ) {
        scope.launch {
            _events.emit(event)
        }
    }

    /**
     * Helper biar pemanggilan simple
     */
    suspend fun showMessage(
        message: String,
        kind: SnackbarKind = SnackbarKind.User
    ) {
        send(SnackbarEvent(message = message, kind = kind))
    }
}
