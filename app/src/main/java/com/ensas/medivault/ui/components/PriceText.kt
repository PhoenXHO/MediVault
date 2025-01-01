package com.ensas.medivault.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ensas.medivault.ui.theme.Typography

// Display the price with the decimal part smaller in size
@Composable
fun PriceText(price: Double, style: TextStyle = Typography.displayMedium) {
    val priceStr = "%.2f".format(price)
    val (integerPart, decimalPart) = priceStr.split(".")
    Text(
        buildAnnotatedString {
            append(integerPart)
            withStyle(SpanStyle(fontSize = (style.fontSize.value * .6).sp)) {
                append(".")
                append(decimalPart)
                append(" MAD")
            }
        },
        style = style
    )
}

// Preview of the PriceText
@Preview(showBackground = true)
@Composable
fun PriceTextPreview() {
    PriceText(12.99)
}