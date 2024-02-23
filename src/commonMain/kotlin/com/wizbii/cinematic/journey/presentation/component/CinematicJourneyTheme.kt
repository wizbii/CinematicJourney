package com.wizbii.cinematic.journey.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.ktx.blend
import com.wizbii.cinematic.journey.presentation.component.dark.mode.LocalDarkMode
import com.wizbii.cinematicjourney.generated.resources.Nunito_Black
import com.wizbii.cinematicjourney.generated.resources.Nunito_BlackItalic
import com.wizbii.cinematicjourney.generated.resources.Nunito_Bold
import com.wizbii.cinematicjourney.generated.resources.Nunito_BoldItalic
import com.wizbii.cinematicjourney.generated.resources.Nunito_ExtraBold
import com.wizbii.cinematicjourney.generated.resources.Nunito_ExtraBoldItalic
import com.wizbii.cinematicjourney.generated.resources.Nunito_ExtraLight
import com.wizbii.cinematicjourney.generated.resources.Nunito_ExtraLightItalic
import com.wizbii.cinematicjourney.generated.resources.Nunito_Italic
import com.wizbii.cinematicjourney.generated.resources.Nunito_Light
import com.wizbii.cinematicjourney.generated.resources.Nunito_LightItalic
import com.wizbii.cinematicjourney.generated.resources.Nunito_Medium
import com.wizbii.cinematicjourney.generated.resources.Nunito_MediumItalic
import com.wizbii.cinematicjourney.generated.resources.Nunito_Regular
import com.wizbii.cinematicjourney.generated.resources.Nunito_SemiBold
import com.wizbii.cinematicjourney.generated.resources.Nunito_SemiBoldItalic
import com.wizbii.cinematicjourney.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@Composable
fun CinematicJourneyTheme(
    isDarkMode: Boolean = LocalDarkMode.current,
    content: @Composable () -> Unit,
) {

    DynamicMaterialTheme(
        content = content,
        seedColor = Color.Red.blend(Color.Yellow, .25f),
        typography = MaterialTheme.typography.withFontFamily(nunitoFontFamily()),
        useDarkTheme = isDarkMode,
    )

}

@Composable
@OptIn(ExperimentalResourceApi::class)
private fun nunitoFontFamily() = FontFamily(
    Font(Res.font.Nunito_Black, FontWeight.Black, FontStyle.Normal),
    Font(Res.font.Nunito_BlackItalic, FontWeight.Black, FontStyle.Italic),
    Font(Res.font.Nunito_Bold, FontWeight.Bold, FontStyle.Normal),
    Font(Res.font.Nunito_BoldItalic, FontWeight.Bold, FontStyle.Italic),
    Font(Res.font.Nunito_ExtraBold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(Res.font.Nunito_ExtraBoldItalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(Res.font.Nunito_ExtraLight, FontWeight.ExtraLight, FontStyle.Normal),
    Font(Res.font.Nunito_ExtraLightItalic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(Res.font.Nunito_Italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.Nunito_Light, FontWeight.Light, FontStyle.Normal),
    Font(Res.font.Nunito_LightItalic, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.Nunito_Medium, FontWeight.Medium, FontStyle.Normal),
    Font(Res.font.Nunito_MediumItalic, FontWeight.Medium, FontStyle.Italic),
    Font(Res.font.Nunito_Regular, FontWeight.Normal, FontStyle.Normal),
    Font(Res.font.Nunito_SemiBold, FontWeight.SemiBold, FontStyle.Normal),
    Font(Res.font.Nunito_SemiBoldItalic, FontWeight.SemiBold, FontStyle.Italic),
)

private fun Typography.withFontFamily(fontFamily: FontFamily): Typography =
    Typography(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily),
    )
