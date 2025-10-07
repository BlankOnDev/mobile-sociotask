package com.blankon.sociotask.core.designsystem.component

import android.content.res.Configuration
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.blankon.sociotask.core.designsystem.component.attr.ActionMenu
import com.blankon.sociotask.core.designsystem.component.attr.TopBarActions
import com.blankon.sociotask.core.designsystem.component.attr.TopBarArgs
import com.blankon.sociotask.core.designsystem.component.attr.TopBarBackButton
import com.blankon.sociotask.core.designsystem.component.attr.TopBarTitle
import com.blankon.sociotask.core.designsystem.icon.SocioTaskIcon
import com.blankon.sociotask.core.designsystem.theme.SociotaskTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    topBarArgs: TopBarArgs,
    modifier: Modifier = Modifier,
    centerTopBar: Boolean = true,
) = with(topBarArgs) {

    val container = topBarColor ?: MaterialTheme.colorScheme.background
    val navTint = iconBackColor ?: Color.Unspecified
    val titleCol = titleColor ?: Color.Unspecified
    val actionTint = actionMenusColor ?: Color.Unspecified

    if (centerTopBar) {
        CenterAlignedTopAppBar(
            modifier = modifier,
            title = {
                title?.let { TopBarTitle(text = it) }
            },
            navigationIcon = {
                if (iconBack != null || onClickBack != null) {
                    TopBarBackButton(
                        iconRes = iconBack,
                        tint = navTint,
                        onClickBack = onClickBack
                    )
                }
            },
            actions = {
                TopBarActions(
                    items = actionMenus,
                    iconTint = actionTint
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = container,
                navigationIconContentColor = navTint,
                titleContentColor = titleCol,
                actionIconContentColor = actionTint
            )
        )
    } else {
        TopAppBar(
            modifier = modifier,
            title = {
                title?.let { TopBarTitle(text = it) }
            },
            navigationIcon = {
                if (iconBack != null || onClickBack != null) {
                    TopBarBackButton(
                        iconRes = iconBack,
                        tint = navTint,
                        onClickBack = onClickBack
                    )
                }
            },
            actions = {
                TopBarActions(
                    items = actionMenus,
                    iconTint = actionTint
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = container,
                navigationIconContentColor = navTint,
                titleContentColor = titleCol,
                actionIconContentColor = actionTint
            )
        )
    }
}


@Preview(
    name = "Centered + Title only",
    showBackground = true
)
@Composable
private fun AppTopBarPreview_TitleOnly() {
    SociotaskTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AppTopBar(
                topBarArgs = TopBarArgs(
                    title = "Dashboard",
                    iconBack = null,
                    actionMenus = emptyList()
                )
            )
        }
    }
}


@Preview(
    name = "Centered • With back & actions",
    showBackground = true
)
@Composable
private fun AppTopBarPreview_WithActions() {
    SociotaskTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AppTopBar(
                topBarArgs = TopBarArgs(
                    title = "Notifications",
                    iconBack = SocioTaskIcon.back, // asumsi tersedia
                    actionMenus = listOf(
                        ActionMenu(
                            iconRes = SocioTaskIcon.back, // pakai placeholder yg pasti ada
                            name = "Action 1",
                            showBadge = true,
                            onClick = { /* no-op for preview */ }
                        ),
                        ActionMenu(
                            iconRes = SocioTaskIcon.back,
                            name = "Action 2",
                            showBadge = false,
                            onClick = { /* no-op */ }
                        )
                    ),
                    onClickBack = { /* no-op */ }
                ),
                centerTopBar = true
            )
        }
    }
}

@Preview(
    name = "Aligned Start • No center",
    showBackground = true
)
@Composable
private fun AppTopBarPreview_NoCenter() {
    SociotaskTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AppTopBar(
                topBarArgs = TopBarArgs(
                    title = "Settings",
                    iconBack = SocioTaskIcon.back,
                    actionMenus = emptyList(),
                    onClickBack = { /* no-op */ }
                ),
                centerTopBar = false
            )
        }
    }
}

@Preview(
    name = "Dark mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AppTopBarPreview_Dark() {
    SociotaskTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AppTopBar(
                topBarArgs = TopBarArgs(
                    title = "Profile",
                    iconBack = null,
                    actionMenus = emptyList()
                ),
                centerTopBar = true
            )
        }
    }
}