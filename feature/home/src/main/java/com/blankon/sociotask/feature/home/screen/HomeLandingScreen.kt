package com.blankon.sociotask.feature.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.blankon.sociotask.core.designsystem.theme.SociotaskTheme
import com.blankon.sociotask.core.domain.dashboard.model.Balance
import com.blankon.sociotask.core.domain.dashboard.model.SocialPlatform
import com.blankon.sociotask.core.domain.dashboard.model.Task
import com.blankon.sociotask.feature.home.viewmodel.HomeEvent
import com.blankon.sociotask.feature.home.viewmodel.HomeLandingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeLandingScreen(
    navController: NavController,
    viewModel: HomeLandingViewModel = hiltViewModel()
) {
    val uiState by viewModel.ui.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }


// events -> snackbar
    val lifecycle = LocalLifecycleOwner.current
    LaunchedEffect(lifecycle) {
        viewModel.events.collect { e ->
            when (e) {
                is HomeEvent.ShowMessage -> snackbarHostState.showSnackbar(e.message)
            }
        }
    }


    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.openForm(true) }) {
                Icon(Icons.Default.Add, contentDescription = "Buat task")
            }
        }
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            } else {
                DashboardContent(
                    balance = uiState.balance,
                    tasks = uiState.tasks
                )
            }
        }


        if (uiState.isFormOpen) {
            ModalBottomSheet(
                onDismissRequest = { viewModel.openForm(false) },
                sheetState = sheetState
            ) {
                TaskForm(
                    state = uiState.form,
                    submitting = uiState.creationInFlight,
                    onChange = viewModel::onFormChange,
                    onSubmit = viewModel::submit,
                    onClose = { viewModel.openForm(false) }
                )
            }
        }
    }
}

@Composable
fun DashboardContent(
    balance: Balance,
    tasks: List<Task>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { BalanceCard(balance) }
        item { Text("Available Task", style = MaterialTheme.typography.titleMedium) }
        items(tasks, key = { it.id }) { task -> TaskCard(task) }
        item { Spacer(Modifier.height(24.dp)) }
    }
}

@Composable
private fun BalanceCard(balance: Balance) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFF6C2BD9),
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    "Your Balance",
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "${balance.points} Pts",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Box(
                Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = .15f))
            ) {}
        }
    }
}


@Composable
private fun TaskCard(task: Task) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(platformColor(task.platform))
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    task.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    task.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "+${task.reward.amount} ${task.reward.unit}",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    task.paymentType.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun platformColor(p: SocialPlatform): Color = when (p) {
    SocialPlatform.Instagram -> Color(0xFFE1306C)
    SocialPlatform.X -> Color(0xFF0F1419)
    SocialPlatform.TikTok -> Color(0xFF69C9D0)
    SocialPlatform.YouTube -> Color(0xFFFF0000)
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