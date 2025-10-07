package com.blankon.sosiotask.core.ui.base

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.blankon.sociotask.core.designsystem.component.AppTopBar
import com.blankon.sociotask.core.designsystem.component.attr.TopBarArgs
import com.blankon.sociotask.core.designsystem.theme.Dimens
import com.blankon.sosiotask.core.ui.activity.LocalActivity

/**
 * Kerangka dasar screen:
 * - Optional kunci orientasi portrait (side-effect aman)
 * - Kelola insets (status/nav bars)
 * - Bisa overlay top bar di atas konten (clipToTopBar=true) atau mendorong konten ke bawah
 * - Top bar bisa via slot custom, atau gunakan overload dengan [TopBarArg] dari Design System
 */
@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    topBarArgs: TopBarArgs = TopBarArgs(),
    centerTopBar: Boolean = false,
    clipToTopBar: Boolean = false,
    lockOrientation: Boolean = true,
    showDefaultTopBar: Boolean = true,
    imePadding: Boolean = true,
    systemBarsPadding: Boolean = true,
    horizontalPadding: Dp = Dimens.Dp24,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {
    val activity = if (lockOrientation) LocalActivity.current else null
    if (lockOrientation && activity != null) {
        DisposableEffect(activity) {
            val previous = activity.requestedOrientation
            try {
                activity.requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
            } catch (_: Throwable) {
            }
            onDispose {
                try {
                    activity.requestedOrientation = previous
                } catch (_: Throwable) {
                }
            }
        }
    }
    val base = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .then(if (systemBarsPadding) Modifier.systemBarsPadding() else Modifier)
        .then(if (imePadding) Modifier.imePadding() else Modifier)
        .padding(horizontal = horizontalPadding)

    if (clipToTopBar) {
        Box(modifier = base) {
            content(PaddingValues(0.dp))
            if (showDefaultTopBar) {
                AppTopBar(
                    topBarArgs = topBarArgs,
                    centerTopBar = centerTopBar,
                    modifier = Modifier
                        .zIndex(1f)
                        .statusBarsPadding()
                        .padding(horizontal = 0.dp)
                )
            }
        }
    } else {
        Column(modifier = base) {
            if (showDefaultTopBar) {
                AppTopBar(
                    topBarArgs = topBarArgs,
                    centerTopBar = centerTopBar
                )
            }
            Box(modifier = Modifier.padding(contentPadding)) {
                content(contentPadding)
            }
        }
    }
}