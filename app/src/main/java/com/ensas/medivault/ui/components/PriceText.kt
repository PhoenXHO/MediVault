package com.ensas.medivault.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ensas.medivault.ui.theme.MediVaultTheme
import com.ensas.medivault.ui.theme.Typography

// Composable to display a price with the decimal part styled differently
@Composable
fun PriceText(
    price: Double,
    style: TextStyle = Typography.displayMedium,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    val priceStr = "%.2f".format(price)
    val (integerPart, decimalPart) = priceStr.split(".")
    // Build annotated string with styled decimal part
    Text(
        buildAnnotatedString {
            append(integerPart)
            withStyle(SpanStyle(fontSize = (style.fontSize.value * .6).sp)) {
                append(".")
                append(decimalPart)
                append(" MAD")
            }
        },
        style = style,
        color = color,
    )
}

@Preview(showBackground = true)
@Composable
fun PriceTextPreview() {
    MediVaultTheme {
        PriceText(12.99)
    }
}