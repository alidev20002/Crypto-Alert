package com.alidev.cryptoalert.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF9142FF),
    onPrimary = Color(0xFFFFFFFF),
    onPrimaryContainer = Color(0xFF444444),
    secondary = Color(0xFFAFAEAE),
    onSecondary = Color(0xFF30E0A1),
    error = Color(0xFFE6445D),
    background = Color(0xFF1E1E1E),
    onBackground = Color(0xFFAFAEAE),
    surface = Color(0xFF272727),
    onSurface = Color(0xFF444444)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF9142FF),
    onPrimary = Color(0xFFFFFFFF),
    onPrimaryContainer = Color(0xFF444444),
    secondary = Color(0xFFAFAEAE),
    onSecondary = Color(0xFF30E0A1),
    error = Color(0xFFE6445D),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFFAFA1A1)


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun CryptoAlertTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
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