package com.blankon.sociotask.feature.home.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices.PIXEL_4
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blankon.sociotask.core.designsystem.theme.SociotaskTheme
import com.blankon.sociotask.core.domain.dashboard.model.Balance
import com.blankon.sociotask.core.domain.dashboard.model.PaymentType
import com.blankon.sociotask.core.domain.dashboard.model.Reward
import com.blankon.sociotask.core.domain.dashboard.model.SocialPlatform
import com.blankon.sociotask.core.domain.dashboard.model.Task
import com.blankon.sociotask.core.ui.BalanceCard
import com.blankon.sociotask.core.ui.TaskItem
import com.blankon.sociotask.feature.home.R
import com.blankon.sociotask.feature.home.viewmodel.HomeLandingViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    onNavigateDetail: (String) -> Unit,
    viewModel: HomeLandingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.ui.collectAsStateWithLifecycle()
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()


//    ObserveAsEvents(viewModel.events) { event ->
//        when (event) {
//            is HomeEvent.ShowMessage -> {
//                val msg = event.message.asString(ctx)
//                SnackbarController.send(
//                    scope,
//                    SnackbarEvent(
//                        message = msg
//                    )
//
//                )
//            }
//
//            is HomeEvent.NavigateDetail -> onNavigateDetail(event.id)
//        }
//    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

//    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { viewModel.openForm(true) }
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Add,
//                    contentDescription = "Create Task"
//                )
//            }
//        }
//    ) { innerPadding ->
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DashboardContent(
            balance = uiState.balance,
            tasks = uiState.tasks,
            modifier = Modifier.background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFBFECF5),
                        Color(0xFFFFFFFF)
                    )
                )
            )
        )

        if (uiState.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
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
            Text(
                "Tempat untuk Task Form",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    }
}

//}

@Composable
fun DashboardContent(
    balance: Balance,
    tasks: List<Task>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Hi, Ramada ! \uD83D\uDC4B",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_sociotask),
                        contentDescription = "Sociotask Logo",
                    )

                    Spacer(Modifier.weight(1f))
                    Box {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notifications",
                            modifier = Modifier.size(28.dp),
                            tint = Color.White
                        )
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color.Red)
                                .align(Alignment.TopEnd)
                                .offset(x = (-4).dp, y = 4.dp)
                        )
                    }

                    Spacer(Modifier.width(16.dp))

                    Image(
                        painter = painterResource(id = R.drawable.placeholder_profile),
                        contentDescription = "Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .border(BorderStroke(1.dp, Color.White), CircleShape)
                    )
                }
            }
        }



        item {
            BalanceCard(
                title = "Your Balance",
                valueText = "${balance.points} Pts",
            )
        }
        item {
            Text(
                text = "Available Task",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        items(tasks, key = { it.id }) { task ->
            TaskItem(
                task = task,
                onClick = {},
                modifier = Modifier.padding(horizontal = 8.dp),
            )
        }
    }
}


@Preview(
    name = "Home - Full device",
    showBackground = true,
    device = PIXEL_4,
    showSystemUi = true
)
@Composable
private fun HomeLandingScreenPreview_Light() {
    val dummyTasks = listOf(
        Task(
            id = "1",
            title = "Follow akun instagram kami",
            platform = SocialPlatform.Instagram,
            description = "Lorem ipsum dolor sit amet",
            link = "",
            platformTint = MaterialTheme.colorScheme.primary,
            reward = Reward(100),
            paymentType = PaymentType.PointsOnly,
            quota = 100,
            deadline = LocalDate.now()
        ),
        Task(
            id = "2",
            title = "Like postingan terbaru",
            platform = SocialPlatform.Instagram,
            description = "Lorem ipsum dolor sit amet",
            link = "",
            platformTint = MaterialTheme.colorScheme.primary,
            reward = Reward(50),
            paymentType = PaymentType.PointsOnly,
            quota = 100,
            deadline = LocalDate.now()
        )
    )

    SociotaskTheme() {
        DashboardContent(
            balance = Balance(50000),
            tasks = dummyTasks,
            modifier = Modifier.background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFBFECF5),
                        Color(0xFFFFFFFF)
                    )
                )
            )
        )
    }
}