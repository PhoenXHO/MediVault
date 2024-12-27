package com.ensas.medivault.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.ensas.medivault.R

val RubikFontFamily = FontFamily(
    Font(R.font.rubik_light, FontWeight.Light),
    Font(R.font.rubik_regular, FontWeight.Normal),
    Font(R.font.rubik_medium, FontWeight.Medium),
    Font(R.font.rubik_bold, FontWeight.Bold),
    Font(R.font.rubik_extrabold, FontWeight.ExtraBold),
    Font(R.font.rubik_black, FontWeight.Black),

    Font(R.font.rubik_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.rubik_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.rubik_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.rubik_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.rubik_extrabold_italic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.rubik_black_italic, FontWeight.Black, FontStyle.Italic),
)