package com.blankon.sociotask.core.designsystem.component.attr

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.blankon.sociotask.core.designsystem.icon.SocioTaskIcon
import com.blankon.sociotask.core.designsystem.theme.Dimens

@Immutable
data class ActionMenu(
    @DrawableRes val iconRes: Int,
    val name: String,
    val showBadge: Boolean = false,
    val onClick: (String) -> Unit
)

@Immutable
data class TopBarArgs(
    val actionMenus: List<ActionMenu> = emptyList(),
    @DrawableRes val iconBack: Int? = null,
    val title: String? = null,
    val topBarColor: Color? = null,
    val titleColor: Color? = null,
    val iconBackColor: Color? = null,
    val actionMenusColor: Color? = null,
    val onClickBack: (() -> Unit)? = null
)

@Composable
fun TopBarBackButton(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int? = SocioTaskIcon.back,
    contentDescription: String? = "Back",
    tint: Color = MaterialTheme.colorScheme.onSurface,
    onClickBack: (() -> Unit)? = null
) {
    IconButton(
        modifier = modifier.clip(CircleShape),
        onClick = { onClickBack?.invoke() },
    ) {
        iconRes?.let {
            Icon(
                imageVector = ImageVector.vectorResource(it),
                contentDescription = contentDescription,
                tint = tint
            )
        }
    }
}

@Composable
fun TopBarActions(
    items: List<ActionMenu>,
    modifier: Modifier = Modifier,
    iconTint: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dimens.Dp16)
    ) {
        items.forEach { item ->
            BadgedBox(
                badge = {
                    if (item.showBadge) {
                        Badge(
                            modifier = Modifier
                                .size(Dimens.Dp15)
                                .border(
                                    width = Dimens.Dp2,
                                    color = MaterialTheme.colorScheme.background,
                                    shape = CircleShape
                                )
                                .align(Alignment.Companion.TopEnd)
                                .clip(CircleShape),
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            ) {
                IconButton(
                    modifier = Modifier.size(Dimens.Dp32),
                    onClick = { item.onClick(item.name) }
                ) {
                    Icon(
                        modifier = Modifier.size(Dimens.Dp24),
                        imageVector = ImageVector.vectorResource(item.iconRes),
                        contentDescription = item.name,
                        tint = iconTint
                    )
                }
            }
        }
    }
}

@Composable
fun TopBarTitle(
    text: String,
) {
    Text(
        text = text,
    )
}
