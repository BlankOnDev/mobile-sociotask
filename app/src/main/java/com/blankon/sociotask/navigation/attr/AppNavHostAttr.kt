package com.blankon.sociotask.navigation.attr

import com.blankon.sociotask.core.navigation.route.HomeGraph.HomeLandingRoute
import com.blankon.sociotask.core.navigation.route.InfoGraph.InfoLandingRoute
import com.blankon.sociotask.core.designsystem.icon.SocioTaskIcon

object AppNavHostAttr {
    data class BottomNavItem<T : Any>(
        val route: T,
        val icon: Int,
        val label: String
    )

    fun getBottomNav() = listOf(
        BottomNavItem(route = HomeLandingRoute, icon = SocioTaskIcon.home, label = "Home"),
        BottomNavItem(route = InfoLandingRoute, icon = SocioTaskIcon.info, label = "Info")
    )
}