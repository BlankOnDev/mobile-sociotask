package com.blankon.sociotask.feature.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.blankon.sociotask.core.designsystem.theme.SociotaskTheme
import com.blankon.sociotask.feature.home.viewmodel.HomeLandingViewModel

@Composable
internal fun HomeLandingScreen(
    navController: NavController,
    homeViewModel: HomeLandingViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun HomeLandingScreenPreview_Light() {
    SociotaskTheme {
        val navController = rememberNavController()
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