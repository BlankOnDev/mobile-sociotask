package com.blankon.sociotask.navigation.attr

import com.blankon.sociotask.core.designsystem.icon.SocioTaskIcon
import com.blankon.sociotask.core.navigation.route.HomeGraph.HomeLandingRoute
import com.blankon.sociotask.core.navigation.route.InfoGraph.InfoLandingRoute

object AppNavHostAttr {
    data class BottomNavItem(
        val route: Any,
        val icon: Int,
        val label: String
    )

    fun getBottomNav() =
        listOf(
            BottomNavItem(
                route = HomeLandingRoute,
                icon = SocioTaskIcon.home,
                label = "Home"
            ),
            BottomNavItem(
                route = InfoLandingRoute,
                icon = SocioTaskIcon.info,
                label = "Info"
            )
        )
}
