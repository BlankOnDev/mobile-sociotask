package com.blankon.sociotask.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blankon.sociotask.core.designsystem.component.SocioListCard
import com.blankon.sociotask.core.designsystem.icon.SocioTaskIcon
import com.blankon.sociotask.core.designsystem.theme.SociotaskTheme
import com.blankon.sociotask.core.domain.model.PaymentType
import com.blankon.sociotask.core.domain.model.Reward
import com.blankon.sociotask.core.domain.model.SocialPlatform
import com.blankon.sociotask.core.domain.model.Task
import java.time.LocalDate

@Composable
fun TaskItem(
    task: Task,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    SocioListCard(
        modifier = modifier,
        onClick = onClick,
        leading = {
            TaskLeadingContent(
                platform = task.platform,
                tint = task.platformTint
            )
        },
        headline = {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        supporting = {
            Text(
                text = task.description,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        trailing = {
            Text(
                text = "+${task.reward.amount}",
                style = MaterialTheme.typography.labelLarge
            )
        }
    )
}


@Composable
private fun TaskLeadingContent(
    platform: SocialPlatform,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(tint)
        )

        Spacer(Modifier.width(12.dp))

        val icon = when (platform) {
            SocialPlatform.Instagram -> SocioTaskIcon.instagram
            SocialPlatform.TikTok -> SocioTaskIcon.tiktok
            SocialPlatform.X -> SocioTaskIcon.twitter
            SocialPlatform.YouTube -> SocioTaskIcon.youtube
        }

        Image(
            painter = painterResource(id = icon),
            contentDescription = platform.toString(),
            modifier = Modifier.size(32.dp),
        )
    }
}

@Preview(showBackground = true, name = "TaskItem - Light")
@Composable
private fun TaskItemPreview() {
    SociotaskTheme(
        darkTheme = false
    ) {
        TaskItem(
            task = Task(
                id = "01",
                title = "Follow akun instagram kami",
                platform = SocialPlatform.Instagram,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                link = "Https://doaifasdho",
                platformTint = Color(0xFF007BFF),
                reward = Reward(
                    amount = 100
                ),
                paymentType = PaymentType.PointsOnly,
                quota = 100,
                deadline = LocalDate.now(),
            ),
            onClick = {}
        )
    }
}