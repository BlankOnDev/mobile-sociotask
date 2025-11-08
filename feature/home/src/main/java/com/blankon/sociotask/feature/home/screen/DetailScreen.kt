package com.blankon.sociotask.feature.home.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blankon.sociotask.core.designsystem.icon.SocioTaskIcon
import com.blankon.sociotask.core.designsystem.theme.SociotaskTheme
import com.blankon.sociotask.feature.home.R

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A68D5),
                        Color(0xFFFFFFFF)
                    )
                )
            )
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                TopBarDetail(
                    onBack = onBack
                )
            }
            item {
                DetailCard()
            }
        }
    }
}

@Composable
fun TopBarDetail(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .clickable { onBack() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFF0F172A)
            )
        }
    }
}

@Composable
private fun DetailCard() {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        tonalElevation = 2.dp,
        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                InfoRow(
                    leading = {
                        Image(
                            painter = painterResource(SocioTaskIcon.instagram),
                            contentDescription = "Instagram",
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    title = "Platform : Instagram"
                )

                InfoRow(
                    leading = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null,
                            tint = Color(0xFF1A68D5)
                        )
                    },
                    title = "Type : Follow"
                )

                InfoRow(
                    leading = {
                        Icon(
                            imageVector = Icons.Outlined.CalendarMonth,
                            contentDescription = null,
                            tint = Color(0xFF1A68D5)
                        )
                    },
                    title = "Due Date : No due date"
                )

                InfoRow(
                    leading = {
                        Icon(
                            imageVector = Icons.Outlined.StarBorder,
                            contentDescription = null,
                            tint = Color(0xFFFFA800)
                        )
                    },
                    title = "Reward : +50 Pts"
                )

                InfoRow(
                    leading = {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF666666)
                        )
                    },
                    title = "Status : Not Started"
                )
            }

            HorizontalDivider(color = Color(0xFFE6E6E6))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF212121)
                )

                val descText = buildAnnotatedString {
                    append("Follow our official Instagram account\n")
                    pushStyle(
                        SpanStyle(
                            color = Color(0xFF1A68D5),
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    append("https://intagram.com/sociotask\n")
                    pop()
                    append("upload an evidence to get reward.")
                }

                Text(
                    text = descText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF555555)
                )
            }


            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Attachment",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF212121)
                )

                AttachmentBox(
                    onClick = {
                        // TODO: buka picker / upload
                    }
                )
            }
        }
    }
}

@Composable
private fun InfoRow(
    leading: @Composable () -> Unit,
    title: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leading()
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF212121)
        )
    }
}

@Composable
private fun AttachmentBox(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                BorderStroke(1.dp, Color(0xFFE0E0E0)),
                RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(SocioTaskIcon.upload),
                contentDescription = "Upload",
                modifier = Modifier.size(28.dp)
            )
            Text(
                text = "Tap to upload your evidence",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF222222)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    SociotaskTheme {
        DetailScreen()
    }
}