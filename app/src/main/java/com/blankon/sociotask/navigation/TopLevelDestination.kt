package com.blankon.sociotask.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.blankon.sociotask.core.designsystem.icon.SocioTaskIcon
import com.blankon.sociotask.feature.home.navigation.HomeBaseRoute
import com.blankon.sociotask.feature.home.navigation.HomeRoute
import com.blankon.sociotask.feature.info.navigation.InfoRoute
import kotlin.reflect.KClass
import com.blankon.sociotask.feature.home.R as homeR
import com.blankon.sociotask.feature.info.R as infoR

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route
) {
    HOME(
        selectedIcon = SocioTaskIcon.home,
        unselectedIcon = SocioTaskIcon.outlineHome,
        iconTextId = homeR.string.feature_home_title,
        titleTextId = homeR.string.feature_home_title,
        route = HomeRoute::class,
        baseRoute = HomeBaseRoute::class,
    ),
    INFO(
        selectedIcon = SocioTaskIcon.info,
        unselectedIcon = SocioTaskIcon.outlineInfo,
        iconTextId = infoR.string.feature_info_title,
        titleTextId = infoR.string.feature_info_title,
        route = InfoRoute::class,
    )
}