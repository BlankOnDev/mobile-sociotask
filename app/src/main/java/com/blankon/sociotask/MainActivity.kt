package com.blankon.sociotask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.blankon.sociotask.core.designsystem.theme.SociotaskTheme
import com.blankon.sociotask.core.navigation.base.BaseNavGraph
import com.blankon.sociotask.navigation.AppNavHost
import com.blankon.sosiotask.core.ui.ObserveAsEvents
import com.blankon.sosiotask.core.ui.SnackbarController
import com.blankon.sosiotask.core.ui.activity.LocalAppSnackbarHostState
import com.blankon.sosiotask.core.ui.activity.ProvideLocalActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navGraphs: Set<@JvmSuppressWildcards BaseNavGraph>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SociotaskTheme {
                val snackbarHostState = remember {
                    SnackbarHostState()
                }
                val scope = rememberCoroutineScope()

                ObserveAsEvents(
                    flow = SnackbarController.events,
                    snackbarHostState
                ) { event ->
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        val result = snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action?.name,
                            duration = event.duration
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            event.action?.action?.invoke()
                        }
                    }
                }
                CompositionLocalProvider(LocalAppSnackbarHostState provides snackbarHostState) {
                    Scaffold(
                        snackbarHost = { SnackbarHost(LocalAppSnackbarHostState.current) }
                    ) { paddingValues ->
                        ProvideLocalActivity(this) {
                            AppNavHost(
                                navGraphs = navGraphs,
                                contentPadding = paddingValues
                            )
                        }
                    }
                }
            }
        }
    }
}