package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CopilotDarkColorScheme = darkColorScheme(
    primary = CopilotPrimary,
    onPrimary = CopilotOnPrimary,
    primaryContainer = CopilotPrimaryContainer,
    onPrimaryContainer = CopilotOnPrimaryContainer,
    secondary = CopilotSecondary,
    onSecondary = CopilotOnSecondary,
    secondaryContainer = CopilotSecondaryContainer,
    onSecondaryContainer = CopilotOnSecondaryContainer,
    tertiary = CopilotTertiary,
    onTertiary = CopilotOnTertiary,
    tertiaryContainer = CopilotTertiaryContainer,
    onTertiaryContainer = CopilotOnTertiaryContainer,
    background = CopilotSurface,
    onBackground = CopilotOnSurface,
    surface = CopilotSurface,
    onSurface = CopilotOnSurface,
    surfaceVariant = CopilotSurfaceContainerHighest,
    onSurfaceVariant = CopilotOnSurfaceVariant,
    outline = CopilotOutline,
    outlineVariant = CopilotOutlineVariant,
    error = CopilotError,
    onError = CopilotOnError,
    errorContainer = CopilotErrorContainer,
    onErrorContainer = CopilotOnErrorContainer
)

@Composable
fun AICareerCopilotTheme(
    darkTheme: Boolean = true, // Force dark-first as specified by Synthetic Vanguard design
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CopilotDarkColorScheme,
        typography = Typography,
        content = content
    )
}
