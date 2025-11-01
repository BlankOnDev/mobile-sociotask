package com.blankon.sociotask.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blankon.sociotask.core.designsystem.icon.SocioTaskIcon
import com.blankon.sociotask.core.designsystem.theme.SociotaskTheme

@Composable
fun BalanceCard(
    title: String,
    valueText: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.secondary,
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = {
        Image(
            painter = painterResource(SocioTaskIcon.coin),
            contentDescription = "Money bag",
            modifier = Modifier.size(40.dp)
        )
    }
) {
    Surface(
        shape = MaterialTheme.shapes.large,
        color = containerColor,
        tonalElevation = 2.dp,
        modifier = modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier else Modifier)

    ) {
        Box(
            modifier = Modifier.padding(0.dp)
        ) {
            Image(
                painter = painterResource(com.blankon.sociotask.core.designsystem.R.drawable.bg_balance),
                contentDescription = "Card Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
            )
            Row(
                Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        title,
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = valueText,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
                trailing?.invoke()
            }
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
    SociotaskTheme(
        darkTheme = false
    ) {
        BalanceCard(
            title = "Your Balance",
            valueText = "2,450 Pts",
            containerColor = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(
    name = "BalanceCard - Dark",
    showBackground = true,
    device = Devices.PIXEL_4,
    backgroundColor = 0x000000
)
@Composable
private fun BalanceCardPreview_Dark() {
    SociotaskTheme(
        darkTheme = true
    ) {
        BalanceCard(
            title = "Your Balance",
            valueText = "2,450 Pts",
            containerColor = MaterialTheme.colorScheme.primary
        )
    }
}