package com.blankon.sociotask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.blankon.sociotask.core.navigation.base.BaseNavGraph
import com.blankon.sociotask.core.designsystem.theme.SociotaskTheme
import com.blankon.sociotask.navigation.AppNavHost
import com.blankon.sosiotask.core.ui.activity.LocalActivity
import dagger.hilt.android.AndroidEntryPoint
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
                Surface(modifier = Modifier.fillMaxSize()) {
                    CompositionLocalProvider(LocalActivity provides this) {
                        AppNavHost(navGraphs = navGraphs)

                    }
                }
            }
        }
    }
}