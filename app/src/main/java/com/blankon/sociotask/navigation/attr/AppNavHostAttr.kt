package com.blankon.sociotask.navigation.attr

import com.blankon.sociotask.core.designsystem.icon.SocioTaskIcon
import com.blankon.sociotask.core.navigation.route.HomeGraph.HomeLandingRoute
import com.blankon.sociotask.core.navigation.route.InfoGraph.InfoLandingRoute
import kotlin.reflect.KClass

object AppNavHostAttr {
    data class BottomNavItem(
        val route: KClass<*>,
        val icon: Int,
        val label: String
    )

    fun getBottomNav() = listOf(
        BottomNavItem(route = HomeLandingRoute::class, icon = SocioTaskIcon.home, label = "Home"),
        BottomNavItem(route = InfoLandingRoute::class, icon = SocioTaskIcon.info, label = "Info")
    )
}