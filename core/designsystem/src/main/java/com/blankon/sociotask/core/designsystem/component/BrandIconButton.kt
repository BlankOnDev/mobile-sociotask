package com.blankon.sociotask.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.blankon.sociotask.core.designsystem.R

@Composable
fun BrandIconButton(
    painter: Painter,
    modifier: Modifier = Modifier,
    contentDescription: String?,
    onClick: () -> Unit,
    containerColor: Color = Color.White,
    cornerRadius: Dp = 16.dp,
    elevation: Dp = 6.dp,
    width: Dp = 64.dp,
    height: Dp = 48.dp,
    border: BorderStroke? = BorderStroke(
        1.dp,
        MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)
    ),
    contentPadding: Dp = 12.dp
) {
    val shape = RoundedCornerShape(cornerRadius)
    Surface(
        modifier = modifier
            .size(width = width, height = height)
            .clip(shape)
            .clickable(
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = onClick
            ),
        shape = shape,
        color = containerColor,
        tonalElevation = 0.dp,
        shadowElevation = elevation,
        border = border
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}


@Preview
@Composable
fun SocialButtonsRow() {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Google (PNG/SVG/vector -> lebih aman pakai painterResource)
        BrandIconButton(
            painter = painterResource(id = R.drawable.logo_google),
            contentDescription = "Sign in with Google",
            onClick = { /* TODO */ },
            // Optional: mirip screenshot (sedikit lebih rounded & bayangan lembut)
            cornerRadius = 18.dp,
            elevation = 8.dp,
            width = 64.dp,
            height = 48.dp,
            contentPadding = 10.dp
        )

        // Twitter/X
        BrandIconButton(
            painter = painterResource(id = R.drawable.logo_twitter),
            contentDescription = "Continue with Twitter",
            onClick = { /* TODO */ },
            cornerRadius = 18.dp,
            elevation = 8.dp,
            width = 64.dp,
            height = 48.dp,
            contentPadding = 10.dp
        )
    }
}