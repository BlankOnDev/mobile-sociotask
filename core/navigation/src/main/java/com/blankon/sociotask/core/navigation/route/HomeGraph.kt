package com.blankon.sociotask.core.navigation.route

import com.blankon.sociotask.core.navigation.helper.generateCustomNavType
import kotlinx.serialization.Serializable

@Serializable
sealed class HomeGraph {
    @Serializable
    data object HomeLandingRoute : HomeGraph()
}