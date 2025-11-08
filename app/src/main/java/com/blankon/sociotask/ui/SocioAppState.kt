package com.blankon.sociotask.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.blankon.sociotask.core.data.utils.NetworkMonitor
import com.blankon.sociotask.feature.home.navigation.navigateToHome
import com.blankon.sociotask.feature.info.navigation.navigateToInfo
import com.blankon.sociotask.navigation.TopLevelDestination
import com.blankon.sociotask.navigation.TopLevelDestination.HOME
import com.blankon.sociotask.navigation.TopLevelDestination.INFO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.reflect.KClass

@Composable
fun rememberSocioAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): SocioAppState {
    return remember(navController, coroutineScope, networkMonitor) {
        SocioAppState(
            navController = navController,
            coroutineScope = coroutineScope,
            networkMonitor = networkMonitor
        )
    }
}


@Stable
class SocioAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)

    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination.isRouteInHierarchy(topLevelDestination.baseRoute)
            }
        }

    val isOffline = networkMonitor.isOnline
        .map { Boolean::not }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries


    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation : ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }

                launchSingleTop = true
                restoreState = true
            }

            when (topLevelDestination) {
                HOME -> navController.navigateToHome(topLevelNavOptions)
                INFO -> navController.navigateToInfo(topLevelNavOptions)
            }
        }
    }
}

fun NavDestination?.isRouteInHierarchy(route: KClass<*>): Boolean {
    return this?.hierarchy?.any { it.hasRoute(route) } == true
}