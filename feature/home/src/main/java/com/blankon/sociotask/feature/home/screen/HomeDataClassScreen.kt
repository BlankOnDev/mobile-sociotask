package com.blankon.sociotask.feature.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.blankon.sociotask.core.designsystem.component.attr.TopBarArgs
import com.blankon.sociotask.feature.home.viewmodel.HomeDetailCustomViewModel
import com.blankon.sosiotask.core.ui.base.BaseScreen

@Composable
internal fun HomeDataClassScreen(
    navController: NavController,
    viewModel: HomeDetailCustomViewModel = hiltViewModel()
) = with(viewModel) {
    BaseScreen(
        modifier = Modifier.fillMaxSize(),
        topBarArgs = TopBarArgs(
            title = "Received Data Class Args",
            onClickBack = { navController.popBackStack() }
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GetBiodata(args.data.name)
                GetBiodata(args.data.age.toString())
                GetBiodata(args.data.desc)
            }
        }
    }
}

@Composable
private fun GetBiodata(data: String) {
    Text(
        text = data,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 24.dp)
    )
}