// file: MEPED/app/src/main/java/com/example/meped/presentation/ui/Theme.kt
package com.example.meped.presentation.ui

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// DIUBAH: Menggunakan warna dari palet kustom
private val DarkColorScheme = darkColorScheme(
    primary = Blue,
    secondary = DarkGray,
    tertiary = LightYellow,
    background = DarkGray,
    surface = DarkGray,
    onPrimary = LightBlue,
    onSecondary = LightBlue,
    onTertiary = DarkGray,
    onBackground = LightBlue,
    onSurface = LightBlue
)

// DIUBAH: Menggunakan warna dari palet kustom
private val LightColorScheme = lightColorScheme(
    primary = Blue,
    secondary = DarkGray,
    tertiary = LightYellow,
    background = LightBlue,
    surface = Color.White,
    onPrimary = LightBlue,
    onSecondary = LightBlue,
    onTertiary = DarkGray,
    onBackground = DarkGray,
    onSurface = DarkGray
)

@Composable
fun MEPEDTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}