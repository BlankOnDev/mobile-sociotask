package com.blankon.sociotask.core.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed class AuthGraph {
    @Serializable
    data object SignInRoute : AuthGraph()

    @Serializable
    data object SignUpRoute : AuthGraph()

    @Serializable
    data object ForgotPasswordRoute : AuthGraph()

}