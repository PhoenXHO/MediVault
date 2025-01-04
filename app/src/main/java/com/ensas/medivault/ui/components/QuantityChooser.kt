package com.ensas.medivault.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ensas.medivault.ui.theme.MediVaultTheme
import com.ensas.medivault.ui.theme.Typography

@Composable
fun QuantityChooser(
    quantity: Int,
    stylized: Boolean = true,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Container for the quantity chooser with optional styling
    Surface(
        color = if (stylized) MaterialTheme.colorScheme.tertiary
            else Color.Transparent,
        shape = MaterialTheme.shapes.extraLarge,
        modifier = if (stylized) Modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onTertiary,
                shape = MaterialTheme.shapes.extraLarge
            )
            else Modifier
    ) {
        // Row layout for the decrease button, quantity display, and increase button
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Button to decrease the quantity
            IconButton(onClick = onDecrease) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Decrease quantity"
                )
            }

            // Text display of the current quantity
            Text(
                text = "$quantity",
                modifier = Modifier.width(20.dp),
                textAlign = TextAlign.Center,
                style = Typography.bodySmall
            )

            // Button to increase the quantity
            IconButton(onClick = onIncrease) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = "Increase quantity"
                )
            }
        }
    }
}

@Preview
@Composable
fun QuantityChooserPreview() {
    MediVaultTheme {
        QuantityChooser(
            quantity = 1,
            onIncrease = { },
            onDecrease = { }
        )
    }
}

@Preview
@Composable
fun QuantityChooserDarkPreview() {
    MediVaultTheme(darkTheme = true) {
        QuantityChooser(
            quantity = 1,
            onIncrease = { },
            onDecrease = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuantityChooserNoStylePreview() {
    MediVaultTheme {
        QuantityChooser(
            quantity = 1,
            stylized = false,
            onIncrease = { },
            onDecrease = { }
        )
    }
}