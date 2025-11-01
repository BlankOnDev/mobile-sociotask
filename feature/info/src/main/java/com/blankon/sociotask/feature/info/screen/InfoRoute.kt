package com.blankon.sociotask.feature.info.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blankon.sociotask.feature.info.viewmodel.InfoViewModel

@Composable
internal fun InfoRoute(
    viewModel: InfoViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .background(Color(0xFFF71B1B)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Thanks for using my template!",
            textAlign = TextAlign.Center,
        )
    }
}