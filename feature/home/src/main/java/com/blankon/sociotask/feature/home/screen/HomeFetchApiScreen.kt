package com.blankon.sociotask.feature.home.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.blankon.sociotask.core.common.utils.state.UiState
import com.blankon.sociotask.core.designsystem.component.attr.TopBarArgs
import com.blankon.sociotask.feature.home.viewmodel.HomeFetchApiViewModel
import com.blankon.sosiotask.core.ui.base.BaseScreen

@Composable
internal fun HomeFetchApiScreen(
    navController: NavController,
    viewModel: HomeFetchApiViewModel = hiltViewModel()
) {
    val uiState = viewModel.ui.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.getSampleData() }

    BaseScreen(
        topBarArgs = TopBarArgs(
            title = "Fetch API Sample",
            onClickBack = { navController.popBackStack() }
        )
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val s = uiState.value) {
                is UiState.StateInitial -> {}
                is UiState.StateLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                        CircularProgressIndicator()
                    }
                }

                is UiState.StateFailed -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                        Text(text = "Failed to fetch data")
                    }
                }

                is UiState.StateSuccess -> {
                    val data = s.data.orEmpty()
                    if (data.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                            Text(text = "No data available")
                        }
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(
                                items = data,
                                key = { it.id }
                            ) {
                                Text(
                                    text = "${it.id}. ${it.description}",
                                    maxLines = 1,
                                    overflow = Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}