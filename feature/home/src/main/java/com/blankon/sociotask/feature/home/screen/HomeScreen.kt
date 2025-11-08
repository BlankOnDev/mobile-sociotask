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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import com.blankon.sociotask.core.designsystem.icon.SocioTaskIcon
import com.blankon.sociotask.core.designsystem.theme.SociotaskTheme
import com.blankon.sociotask.core.domain.model.Balance
import com.blankon.sociotask.core.domain.model.PaymentType
import com.blankon.sociotask.core.domain.model.Reward
import com.blankon.sociotask.core.domain.model.SocialPlatform
import com.blankon.sociotask.core.domain.model.Task
import com.blankon.sociotask.core.ui.BalanceCard
import com.blankon.sociotask.core.ui.TaskItem
import com.blankon.sociotask.feature.home.R
import com.blankon.sociotask.feature.home.viewmodel.HomeLandingViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    onDetailClick: () -> Unit,
    viewModel: HomeLandingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.ui.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    Box(modifier = Modifier.fillMaxSize()) {
        DashboardContent(
            balance = uiState.balance,
            tasks = uiState.tasks,
            modifier = Modifier.fillMaxSize(),
            onDetailClick = onDetailClick
        )

        FloatingActionButton(
            onClick = { viewModel.openForm(true) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 20.dp)

        ) {
            Icon(
                imageVector = (SocioTaskIcon.Add),
                contentDescription = null
            )
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
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
    modifier: Modifier = Modifier,
    onDetailClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        Color(0xFF1A68D5), shape = RoundedCornerShape(
                            bottomEnd = 24.dp,
                            bottomStart = 24.dp
                        )
                    )
                    .padding(start = 28.dp, end = 28.dp, top = 30.dp, bottom = 80.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_sociotask),
                        contentDescription = "Sociotask Logo",
                        modifier = Modifier.height(48.dp)
                    )
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        item {
            BalanceCard(
                userName = "Ramada",
                totalBalance = "${balance.points} Pts",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .offset(y = (-70).dp)
            )
        }

        item {
            Text(
                text = "Available Task",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .offset(y = (-64).dp)
            )
        }

        items(tasks, key = { it.id }) { task ->
            TaskItem(
                task = task,
                onClick = onDetailClick,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .offset(y = (-56).dp),
            )
        }
    }
}

@Preview(
    name = "Home - Full device",
    showBackground = true,
    device = PIXEL_4,
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
            onDetailClick = {}
        )
    }
}