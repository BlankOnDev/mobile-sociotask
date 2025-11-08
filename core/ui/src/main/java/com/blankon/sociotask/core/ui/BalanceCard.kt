package com.blankon.sociotask.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blankon.sociotask.core.designsystem.theme.SociotaskTheme
import com.blankon.sociotask.core.designsystem.R as DesignSystemR

@Composable
fun BalanceCard(
    userName: String,
    totalBalance: String,
    modifier: Modifier = Modifier,
    profileImageResId: Int = DesignSystemR.drawable.ic_avatar,
    illustrationImageResId: Int = DesignSystemR.drawable.ic_creditcard,
    containerColor: Color = Color.White,
    textColor: Color = Color.Black,
    onClick: (() -> Unit)? = null,
) {
    Surface(
        shape = MaterialTheme.shapes.large,
        color = containerColor,
        tonalElevation = 2.dp,
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) Modifier.clickable(onClick = onClick)
                else Modifier
            )
            .border(1.dp, Color.LightGray, MaterialTheme.shapes.large)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Hi $userName",
                    color = textColor,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Welcome back",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Image(
                        painter = painterResource(id = profileImageResId),
                        contentDescription = "User Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.LightGray, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = "Total Balance",
                            color = Color.Gray,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = totalBalance,
                            color = textColor,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
            }

            Image(
                painter = painterResource(id = illustrationImageResId),
                contentDescription = "Savings Illustration",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(80.dp)
            )
        }
    }
}

@Preview(
    name = "BalanceCard - Light",
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
private fun BalanceCardPreview_Light() {
    SociotaskTheme(darkTheme = false) {
        Column(
            modifier = Modifier
                .background(Color(0xFFF0F2F5))
                .padding(16.dp)
        ) {
            BalanceCard(
                userName = "Jack Mack",
                totalBalance = "$45,567.00",
                profileImageResId = DesignSystemR.drawable.placeholder_profile,
                illustrationImageResId = DesignSystemR.drawable.money_bag,
                containerColor = Color.White,
                textColor = Color.Black
            )
        }
    }
}

@Preview(
    name = "BalanceCard - Dark",
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
private fun BalanceCardPreview_Dark() {
    SociotaskTheme(darkTheme = true) {
        Column(
            modifier = Modifier
                .background(Color.DarkGray)
                .padding(16.dp)
        ) {
            BalanceCard(
                userName = "Jack Mack",
                totalBalance = "$45,567.00",
                profileImageResId = DesignSystemR.drawable.placeholder_profile,
                illustrationImageResId = DesignSystemR.drawable.money_bag,
                containerColor = Color(0xFF2C2C2E),
                textColor = Color.White
            )
        }
    }
}