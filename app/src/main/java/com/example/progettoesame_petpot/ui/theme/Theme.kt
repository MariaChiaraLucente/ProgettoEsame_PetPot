package com.example.progettoesame_petpot.ui.theme

import android.app.Activity
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
import com.example.progettoesame_petpot.Home.Components.ThemeSettings

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF3A4FEE),
    secondary = Color(0xff22324a), //CARD
    onSecondary = Color(0xFF212121), //TESTO
    tertiary = Color(0xFF232B3E), //NAVBAR
    background = Color(0xFF1C2639), //SFONDO
    onBackground = Color(0xffd0d0d0), //TESTO
    surface = Color(0xFF30488B), //BOTTONE
    error = Color(0xFFA54747),
    outline = Color(0xff262a37), //BORDER
    inversePrimary = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2E3EB8),
    secondary = Color(0xFF8099C9), //CARD
    onSecondary = Color(0xFF212121), //TESTO
    tertiary = Color(0xFF2D4465), //NAVBAR
    background = Color(0xFF5576B4), //SFONDO
    onBackground = Color(0xfff0f0f0), //TESTO
    surface = Color(0xFF2E3EB8), //BOTTONE
    error = Color(0xffa26262),
    outline = Color(0xff607191), //BORDER
    inversePrimary = Color.Black,



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
fun ProgettoEsamePetPotTheme(
    darkTheme: Boolean = ThemeSettings.isDarkMode,
    //darkTheme: Boolean = isSystemInDarkTheme(),
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
        content = content
    )
}