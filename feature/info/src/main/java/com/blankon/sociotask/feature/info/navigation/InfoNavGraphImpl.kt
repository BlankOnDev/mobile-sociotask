package com.blankon.sociotask.feature.info.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.blankon.sociotask.core.navigation.base.BaseNavGraph
import com.blankon.sociotask.core.navigation.helper.composableScreen
import com.blankon.sociotask.core.navigation.route.InfoGraph.InfoLandingRoute
import com.blankon.sociotask.feature.info.screen.InfoLandingScreen
import javax.inject.Inject

class InfoNavGraphImpl @Inject constructor() : BaseNavGraph {
    override fun NavGraphBuilder.createGraph(navController: NavController) {
        composableScreen<InfoLandingRoute>(
            enterTransition = fadeIn(),
            popExitTransition = fadeOut()
        ) { InfoLandingScreen() }
    }
}