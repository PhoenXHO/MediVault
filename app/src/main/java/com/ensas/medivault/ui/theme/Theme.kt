package com.ensas.medivault.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = RadicalRed,
    secondary = ColdLips,
    tertiary = ConnecticutLilac,

    surface = Jaguar,

    onSurface = Color.White,
    onPrimary = Color.White,
    onTertiary = ColdLips,
    onSecondaryContainer = Color.White,
    onBackground = Color.White,
    scrim = Indigo,
)

private val LightColorScheme = lightColorScheme(
    primary = RadicalRed,
    secondary = SpectrumBlue,
    tertiary = ColdLips,

    background = WhisperBlue,
    surface = Color.White,
    scrim = Color.White,

    secondaryContainer = Jaguar,

    onSurface = Color.Black,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = SpectrumBlue,
    onSecondaryContainer = Color.White,
    onBackground = Color.Black,
)

@Composable
fun MediVaultTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}