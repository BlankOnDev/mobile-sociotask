package com.blankon.sociotask.navigation.attr

import androidx.compose.ui.graphics.vector.ImageVector
import com.blankon.sociotask.core.designsystem.icon.SocioTaskIcon
import com.blankon.sociotask.feature.home.navigation.HomeRoute
import com.blankon.sociotask.feature.info.navigation.InfoRoute

object AppNavHostAttr {
    data class BottomNavItem(
        val route: Any,
        val icon: ImageVector,
        val label: String
    )

    fun getBottomNav() =
        listOf(
            BottomNavItem(
                route = HomeRoute,
                icon = SocioTaskIcon.home,
                label = "Home"
            ),
            BottomNavItem(
                route = InfoRoute,
                icon = SocioTaskIcon.info,
                label = "Info"
            )
        )
}
