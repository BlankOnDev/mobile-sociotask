package com.blankon.sociotask.feature.info.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blankon.sociotask.feature.info.viewmodel.InfoViewModel

@Composable
internal fun InfoLandingScreen(
    viewModel: InfoViewModel = hiltViewModel()
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Thanks for using my template!",
            textAlign = TextAlign.Center,
        )
    }

}