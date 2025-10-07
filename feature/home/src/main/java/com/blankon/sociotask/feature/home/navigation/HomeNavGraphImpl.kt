package com.blankon.sociotask.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.blankon.sociotask.core.navigation.base.BaseNavGraph
import com.blankon.sociotask.core.navigation.helper.composableScreen
import com.blankon.sociotask.core.navigation.route.HomeGraph.HomeDataClassRoute
import com.blankon.sociotask.core.navigation.route.HomeGraph.HomeDataTypeRoute
import com.blankon.sociotask.core.navigation.route.HomeGraph.HomeFetchApiRoute
import com.blankon.sociotask.core.navigation.route.HomeGraph.HomeLandingRoute
import com.blankon.sociotask.feature.home.screen.HomeDataClassScreen
import com.blankon.sociotask.feature.home.screen.HomeDataTypeScreen
import com.blankon.sociotask.feature.home.screen.HomeFetchApiScreen
import com.blankon.sociotask.feature.home.screen.HomeLandingScreen
import javax.inject.Inject

class HomeNavGraphImpl @Inject constructor() : BaseNavGraph {
    override fun NavGraphBuilder.createGraph(navController: NavController) {
        composableScreen<HomeLandingRoute> {
            HomeLandingScreen(navController)
        }
        composableScreen<HomeDataTypeRoute> {
            HomeDataTypeScreen(navController)
        }
        composableScreen<HomeDataClassRoute>(
            customArgs = HomeDataClassRoute.typeMap
        ) {
            HomeDataClassScreen(navController)
        }
        composableScreen<HomeFetchApiRoute> {
            HomeFetchApiScreen(navController)
        }
    }
}