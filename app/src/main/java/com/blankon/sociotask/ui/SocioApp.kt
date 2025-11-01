package com.blankon.sociotask.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.blankon.sociotask.core.ui.activity.LocalAppSnackbarHostState
import com.blankon.sociotask.navigation.AppNavHost

@Composable
fun SocioApp(
    appState: SocioAppState,
    startDestination: Any,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // NOTE: listener snackbar global, network, dsb. bisa tetap di sini
    // (kalau kamu pakai SnackbarController versi sebelumnya tinggal copas lagi)


    CompositionLocalProvider(LocalAppSnackbarHostState provides snackbarHostState) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { innerPadding ->
            val topLevelDestination = appState.topLevelDestinations
            val currentDestination = appState.currentDestination

            val showBottomNav =
                currentDestination?.hierarchy?.any { dest ->
                    topLevelDestination.any { item -> dest.hasRoute(item.route) }
                } == true

            Box(contentAlignment = Alignment.BottomCenter) {

                AppNavHost(
                    appState = appState,
                    startDestination = startDestination,
                    modifier = modifier.padding(innerPadding)
                )

                AnimatedVisibility(
                    visible = showBottomNav,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {


                    BottomAppBar(
                        containerColor = MaterialTheme.colorScheme.background,
                    ) {
                        topLevelDestination.forEach { destination ->
                            val selected =
                                currentDestination.isRouteInHierarchy(destination.baseRoute)

                            NavigationBarItem(
                                selected = selected,
                                onClick = {
                                    appState.navigateToTopLevelDestination(destination)
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                                        contentDescription = null
                                    )
                                },
                                label = { Text(stringResource(destination.iconTextId)) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    unselectedIconColor = Gray,
                                    unselectedTextColor = Gray,
                                    indicatorColor = Transparent
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
