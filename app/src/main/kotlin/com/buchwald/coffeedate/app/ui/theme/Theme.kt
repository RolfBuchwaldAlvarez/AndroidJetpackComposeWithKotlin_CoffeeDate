package com.buchwald.coffeedate.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Definition des Light-Mode-Farbschemas
private val LightColorScheme = lightColorScheme(
    // Primärfarben
    primary = GradientStart, // Die primäre Farbe des Farbverlaufs
    secondary = GradientEnd, // Die sekundäre Farbe des Farbverlaufs

    // Oberflächenfarben
    surface = Surface, // Lila Oberfläche (z. B. "Mo")
    surfaceVariant = SurfaceVariant, // Weiße Kartenoberfläche (z. B. "Erwin & Jana")

    // Text- und Icon-Farben
    onSurface = OnSurface, // Weißer Text auf lilafarbenen Oberflächen
    onSurfaceVariant = OnSurfaceVariant, // Blauer Text auf weißen Karten
    onSecondary = OnSurfaceSecondary // Hellblauer Text auf sekundären Elementen (z. B. Details)
)

// Dark Mode übernimmt die Farben des Light Modes (identisch)
private val DarkColorScheme = LightColorScheme

@Composable
fun CoffeeDateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Auswahl des Farbschemas basierend auf dem Modus
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    // Anwendung des Material-Themes mit den Farben, Typografien und dem Inhalt
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}