package com.blankon.sociotask.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.blankon.sociotask.auth.screen.SignInScreen
import com.blankon.sociotask.auth.screen.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable
data object AuthBaseRoute

@Serializable
data object AuthSignInRoute

@Serializable
data object AuthSignUpRoute

@Serializable
data object AuthForgotPasswordRoute

fun NavController.navigateToAuth(navOptions: NavOptions? = null) {
    if (navOptions != null) {
        navigate(AuthSignInRoute, navOptions)
    } else {
        navigate(AuthSignInRoute)
    }
}

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) =
    if (navOptions != null) {
        navigate(route = AuthSignInRoute, navOptions)
    } else {
        navigate(AuthSignInRoute)
    }

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) =
    if (navOptions != null) {
        navigate(route = AuthSignUpRoute, navOptions)
    } else {
        navigate(AuthSignUpRoute)
    }


fun NavGraphBuilder.authGraph(
    onNavigateHome: () -> Unit,
    onNavigateSignUp: () -> Unit,
    onNavigateSignIn: () -> Unit,
    onNavigateForgotPassword: () -> Unit,
) {
    navigation<AuthBaseRoute>(startDestination = AuthSignInRoute) {
        composable<AuthSignInRoute> {
            SignInScreen(
                onNavigateHome = {},
            )
        }

        composable<AuthSignUpRoute> {
            SignUpScreen(
                onNavigateLogin = onNavigateSignIn,
            )
        }
    }
}