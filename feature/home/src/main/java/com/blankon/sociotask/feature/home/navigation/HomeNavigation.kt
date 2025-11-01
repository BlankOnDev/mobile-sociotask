package com.blankon.sociotask.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.blankon.sociotask.feature.home.screen.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data object HomeBaseRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    if (navOptions != null) {
        navigate(route = HomeRoute, navOptions)
    } else {
        navigate(HomeRoute)
    }


fun NavGraphBuilder.homeScreen(
    onDetailClick: (String) -> Unit
) {
    navigation<HomeBaseRoute>(startDestination = HomeRoute) {
        composable<HomeRoute>() {
            HomeScreen(onDetailClick)
        }
    }
}
