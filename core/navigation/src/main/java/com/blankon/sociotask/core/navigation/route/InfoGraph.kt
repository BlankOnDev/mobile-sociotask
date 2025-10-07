package com.blankon.sociotask.core.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed class InfoGraph {
    @Serializable
    data object InfoLandingRoute : InfoGraph()
}