package com.blankon.sociotask.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.blankon.sociotask.feature.home.screen.DetailScreen
import com.blankon.sociotask.feature.home.screen.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data object DetailRoute

@Serializable
data object HomeBaseRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    if (navOptions != null) {
        navigate(route = HomeRoute, navOptions)
    } else {
        navigate(HomeRoute)
    }

fun NavController.navigateToDetail(navOptions: NavOptions? = null) =
    if (navOptions != null) {
        navigate(route = DetailRoute, navOptions)
    } else {
        navigate(DetailRoute)
    }


fun NavGraphBuilder.homeScreen(
    onDetailClick: () -> Unit
) {
    navigation<HomeBaseRoute>(startDestination = HomeRoute) {
        composable<HomeRoute>() {
            HomeScreen(onDetailClick)
        }

        composable<DetailRoute> {
            DetailScreen()
        }
    }
}
