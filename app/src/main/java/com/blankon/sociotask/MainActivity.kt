package com.blankon.sociotask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blankon.sociotask.auth.navigation.AuthBaseRoute
import com.blankon.sociotask.auth.navigation.AuthSignInRoute
import com.blankon.sociotask.core.data.utils.NetworkMonitor
import com.blankon.sociotask.core.designsystem.theme.SociotaskTheme
import com.blankon.sociotask.core.domain.auth.usecase.SessionState
import com.blankon.sociotask.core.ui.activity.ProvideLocalActivity
import com.blankon.sociotask.feature.home.navigation.HomeBaseRoute
import com.blankon.sociotask.ui.SocioApp
import com.blankon.sociotask.ui.rememberSocioAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { viewModel.session.value is SessionState.Loading }

        val isDark = resources.configuration.uiMode and
                android.content.res.Configuration.UI_MODE_NIGHT_MASK ==
                android.content.res.Configuration.UI_MODE_NIGHT_YES

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT,
            ) { isDark },
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = lightScrim,
                darkScrim = darkScrim,
            ) { isDark },
        )

        setContent {
            SociotaskTheme {
                val appState = rememberSocioAppState(networkMonitor)
                val session = viewModel.session.collectAsStateWithLifecycle()

                when (session.value) {
                    is SessionState.Authenticated -> {
                        ProvideLocalActivity(this) {
                            SocioApp(
                                appState = appState,
                                startDestination = HomeBaseRoute,
                            )
                        }
                    }

                    SessionState.Unauthenticated -> {
                        ProvideLocalActivity(this) {
                            SocioApp(
                                appState = appState,
                                startDestination = AuthBaseRoute,
                            )
                        }
                    }

                    SessionState.Loading -> {}
                }


            }
        }
    }
}


private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)

data class ThemeSettings(
    val darkTheme: Boolean,
    val androidTheme: Boolean,
    val disableDynamicTheming: Boolean,
)
