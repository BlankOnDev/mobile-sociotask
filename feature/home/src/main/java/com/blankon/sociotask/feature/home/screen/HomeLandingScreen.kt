package com.blankon.sociotask.feature.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.blankon.sociotask.core.navigation.helper.navigateTo
import com.blankon.sociotask.core.navigation.route.HomeGraph.HomeDataClassRoute
import com.blankon.sociotask.core.navigation.route.HomeGraph.HomeDataClassRoute.CustomData
import com.blankon.sociotask.core.navigation.route.HomeGraph.HomeDataTypeRoute
import com.blankon.sociotask.core.navigation.route.HomeGraph.HomeFetchApiRoute
import com.blankon.sociotask.core.designsystem.theme.SociotaskTheme
import com.blankon.sosiotask.core.ui.base.BaseScreen
import timber.log.Timber

@Composable
internal fun HomeLandingScreen(navController: NavController) {
    BaseScreen(showDefaultTopBar = false, lockOrientation = false) { innerPadding ->
        Timber.d("HomeLandingScreen")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)                 // ⬅️ penting untuk hindari overlap insets/topbar
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    navController.navigateTo(
                        HomeDataTypeRoute("This is primitive type data")
                    )
                }
            ) { Text(text = "Navigate with data type args") }

            Spacer(modifier = Modifier.size(24.dp))

            Button(
                onClick = {
                    navController.navigateTo(
                        HomeDataClassRoute(
                            CustomData(
                                name = "Daffa",
                                age = 24,
                                desc = "Hello, I'm Daffa. I am 24 years old and this is a custom data class."
                            )
                        )
                    )
                }
            ) { Text(text = "Navigate with data class args") }

            Spacer(modifier = Modifier.size(24.dp))

            Button(
                onClick = { navController.navigateTo(HomeFetchApiRoute) }
            ) { Text(text = "Fetch API") }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun HomeLandingScreenPreview_Light() {
    SociotaskTheme {
        val navController = rememberNavController()    // ✅ instance NavController
        HomeLandingScreen(navController)
    }
}

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
private fun HomeLandingScreenPreview_Dark() {
    SociotaskTheme(darkTheme = true) {
        val navController = rememberNavController()
        HomeLandingScreen(navController)
    }
}