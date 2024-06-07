package com.clo.accloss.core.presentation.theme

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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary_oc,
    onPrimary = md_theme_dark_onPrimary_oc,
    primaryContainer = md_theme_dark_primaryContainer_oc,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer_oc,
    secondary = md_theme_dark_secondary_oc,
    onSecondary = md_theme_dark_onSecondary_oc,
    secondaryContainer = md_theme_dark_secondaryContainer_oc,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer_oc,
    tertiary = md_theme_dark_tertiary_oc,
    onTertiary = md_theme_dark_onTertiary_oc,
    tertiaryContainer = md_theme_dark_tertiaryContainer_oc,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer_oc,
    error = md_theme_dark_error_oc,
    errorContainer = md_theme_dark_errorContainer_oc,
    onError = md_theme_dark_onError_oc,
    onErrorContainer = md_theme_dark_onErrorContainer_oc,
    background = md_theme_dark_background_oc,
    onBackground = md_theme_dark_onBackground_oc,
    surface = md_theme_dark_surface_oc,
    onSurface = md_theme_dark_onSurface_oc,
    surfaceVariant = md_theme_dark_surfaceVariant_oc,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant_oc,
    outline = md_theme_dark_outline_oc,
    inverseOnSurface = md_theme_dark_inverseOnSurface_oc,
    inverseSurface = md_theme_dark_inverseSurface_oc,
    inversePrimary = md_theme_dark_inversePrimary_oc,
    surfaceTint = md_theme_dark_surfaceTint_oc,
    outlineVariant = md_theme_dark_outlineVariant_oc,
    scrim = md_theme_dark_scrim_oc,
)

val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary_oc,
    onPrimary = md_theme_light_onPrimary_oc,
    primaryContainer = md_theme_light_primaryContainer_oc,
    onPrimaryContainer = md_theme_light_onPrimaryContainer_oc,
    secondary = md_theme_light_secondary_oc,
    onSecondary = md_theme_light_onSecondary_oc,
    secondaryContainer = md_theme_light_secondaryContainer_oc,
    onSecondaryContainer = md_theme_light_onSecondaryContainer_oc,
    tertiary = md_theme_light_tertiary_oc,
    onTertiary = md_theme_light_onTertiary_oc,
    tertiaryContainer = md_theme_light_tertiaryContainer_oc,
    onTertiaryContainer = md_theme_light_onTertiaryContainer_oc,
    error = md_theme_light_error_oc,
    errorContainer = md_theme_light_errorContainer_oc,
    onError = md_theme_light_onError_oc,
    onErrorContainer = md_theme_light_onErrorContainer_oc,
    background = md_theme_light_background_oc,
    onBackground = md_theme_light_onBackground_oc,
    surface = md_theme_light_surface_oc,
    onSurface = md_theme_light_onSurface_oc,
    surfaceVariant = md_theme_light_surfaceVariant_oc,
    onSurfaceVariant = md_theme_light_onSurfaceVariant_oc,
    outline = md_theme_light_outline_oc,
    inverseOnSurface = md_theme_light_inverseOnSurface_oc,
    inverseSurface = md_theme_light_inverseSurface_oc,
    inversePrimary = md_theme_light_inversePrimary_oc,
    surfaceTint = md_theme_light_surfaceTint_oc,
    outlineVariant = md_theme_light_outlineVariant_oc,
    scrim = md_theme_light_scrim_oc,
)

@Composable
fun ACCLOSSTheme(
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            window.navigationBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
