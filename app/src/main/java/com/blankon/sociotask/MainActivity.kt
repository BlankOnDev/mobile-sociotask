package com.blankon.sociotask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blankon.sociotask.core.data.utils.NetworkMonitor
import com.blankon.sociotask.core.designsystem.theme.SociotaskTheme
import com.blankon.sociotask.core.domain.auth.usecase.SessionState
import com.blankon.sociotask.core.navigation.base.BaseNavGraph
import com.blankon.sociotask.core.navigation.route.AuthGraph
import com.blankon.sociotask.core.navigation.route.HomeGraph
import com.blankon.sociotask.navigation.AppNavHost
import com.blankon.sosiotask.core.ui.ObserveAsEvents
import com.blankon.sosiotask.core.ui.SnackbarController
import com.blankon.sosiotask.core.ui.activity.LocalAppSnackbarHostState
import com.blankon.sosiotask.core.ui.activity.ProvideLocalActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var navGraphs: Set<@JvmSuppressWildcards BaseNavGraph>

    @Inject lateinit var networkMonitor: NetworkMonitor

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { viewModel.session.value is SessionState.Loading }

        enableEdgeToEdge()

        setContent {
            SociotaskTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                ObserveAsEvents(flow = SnackbarController.events, snackbarHostState) { event ->
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        val result =
                                snackbarHostState.showSnackbar(
                                        message = event.message,
                                        actionLabel = event.action?.name,
                                        duration = event.duration
                                )
                        if (result == SnackbarResult.ActionPerformed) {
                            event.action?.action?.invoke()
                        }
                    }
                }

                LaunchedEffect(Unit) {
                    networkMonitor.isOnline.collect { connected ->
                        if (connected) {
                            snackbarHostState.currentSnackbarData?.dismiss()
                        } else {
                            snackbarHostState.showSnackbar(
                                    message = "Tidak ada koneksi internet",
                                    actionLabel = "Retry"
                            )
                        }
                    }
                }

                val session = viewModel.session.collectAsStateWithLifecycle()

                val start: KClass<*> =
                        when (session.value) {
                            is SessionState.Authenticated -> {
                                HomeGraph.HomeLandingRoute::class
                            }
                            SessionState.Unauthenticated -> {
                                AuthGraph.SignInRoute::class
                            }
                            SessionState.Loading -> {
                                AuthGraph.SignInRoute::class
                            }
                        }

                CompositionLocalProvider(LocalAppSnackbarHostState provides snackbarHostState) {
                    Scaffold(snackbarHost = { SnackbarHost(LocalAppSnackbarHostState.current) }) {
                            paddingValues ->
                        ProvideLocalActivity(this) {
                            AppNavHost(
                                    navGraphs = navGraphs,
                                    contentPadding = paddingValues,
                                    startDestination = start
                            )
                        }
                    }
                }
            }
        }
    }
}
