package com.blankon.sociotask.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.blankon.sociotask.auth.screen.SignInScreen
import com.blankon.sociotask.core.navigation.base.BaseNavGraph
import com.blankon.sociotask.core.navigation.helper.composableScreen
import com.blankon.sociotask.core.navigation.route.AuthGraph.SignInRoute
import com.blankon.sociotask.core.navigation.route.HomeGraph
import javax.inject.Inject

class SignInNavGraphImpl @Inject constructor() : BaseNavGraph {
    override fun NavGraphBuilder.createGraph(navController: NavController) {
        composableScreen<SignInRoute> {
            SignInScreen(
                onNavigateHome = {
                    navController.navigate(HomeGraph.HomeLandingRoute) {
                        popUpTo(SignInRoute) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
            // Jika butuh kirim userId:
            // navController.navigate(HomeGraph.HomeLandingRoute(userId)) {
            //     popUpTo(SignInRoute) { inclusive = true }
            //     launchSingleTop = true
            // }

        }
    }
}