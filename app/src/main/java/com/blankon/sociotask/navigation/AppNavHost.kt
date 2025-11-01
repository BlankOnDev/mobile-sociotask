package com.blankon.sociotask.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.blankon.sociotask.auth.navigation.AuthSignInRoute
import com.blankon.sociotask.auth.navigation.authGraph
import com.blankon.sociotask.auth.navigation.navigateToSignIn
import com.blankon.sociotask.auth.navigation.navigateToSignUp
import com.blankon.sociotask.feature.home.navigation.homeScreen
import com.blankon.sociotask.feature.home.navigation.navigateToHome
import com.blankon.sociotask.feature.info.navigation.infoScreen
import com.blankon.sociotask.ui.SocioAppState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppNavHost(
    appState: SocioAppState,
    startDestination: Any,
    modifier: Modifier = Modifier
) {
    val navController = appState.navController
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        authGraph(
            onNavigateHome = { navController.navigateToHome() },
            onNavigateSignIn = { navController.navigateToSignIn() },
            onNavigateSignUp = { navController.navigateToSignUp() },
            onNavigateForgotPassword = {}
        )

        homeScreen(
            onDetailClick = {}
        )
        infoScreen()
    }
}
