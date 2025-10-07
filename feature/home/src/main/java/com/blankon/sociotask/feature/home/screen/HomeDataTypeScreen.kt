package com.blankon.sociotask.feature.home.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.blankon.sociotask.core.designsystem.component.attr.TopBarArgs
import com.blankon.sociotask.feature.home.viewmodel.HomeDetailViewModel
import com.blankon.sosiotask.core.ui.base.BaseScreen
import androidx.compose.ui.text.style.TextAlign.Companion.Center as TextAlignCenter

@Composable
internal fun HomeDataTypeScreen(
    navController: NavController,
    viewModel: HomeDetailViewModel = hiltViewModel()
) = with(viewModel) {
    BaseScreen(
        topBarArgs = TopBarArgs(
            title = "Received Data Type Args",
            onClickBack = { navController.popBackStack() }
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Center
        ) {
            Text(
                text = args.name,
                textAlign = TextAlignCenter,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    }
}