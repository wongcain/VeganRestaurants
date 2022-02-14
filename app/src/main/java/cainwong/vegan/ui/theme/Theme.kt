package cainwong.vegan.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    CompositionLocalProvider(
        LocalDimens provides Dimens()
    ) {
        MaterialTheme(
            colors = if (darkTheme) {
                DarkColorPalette
            } else {
                LightColorPalette
            },
            content = content
        )
    }
}
