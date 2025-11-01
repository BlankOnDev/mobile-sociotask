package com.blankon.sociotask.feature.info.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.blankon.sociotask.feature.info.screen.InfoRoute
import kotlinx.serialization.Serializable

@Serializable
data object InfoRoute

fun NavController.navigateToInfo(
    navOptions: NavOptions? = null,
) {
    navigate(route = InfoRoute, navOptions)
}

fun NavGraphBuilder.infoScreen(
) {
    composable<InfoRoute> {
        InfoRoute()
    }
}
