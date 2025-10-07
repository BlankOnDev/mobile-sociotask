package com.blankon.sosiotask.core.ui.activity

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext


// Nullable agar aman di Preview
val LocalActivity: ProvidableCompositionLocal<ComponentActivity?> = compositionLocalOf { null }

@Composable
fun ProvideLocalActivity(
    activity: ComponentActivity? = LocalContext.current.findComponentActivity(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalActivity provides activity, content = content)
}

// Helper untuk mencari ComponentActivity dari Context
private tailrec fun Context.findComponentActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is Activity -> this as? ComponentActivity
    is ContextWrapper -> baseContext.findComponentActivity()
    else -> null
}