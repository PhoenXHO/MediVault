
package com.ensas.medivault.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ensas.medivault.ui.theme.Typography

@Composable
fun QuantityChooser(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        IconButton(onClick = onDecrease) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Decrease quantity"
            )
        }

        Text(
            text = "$quantity",
            modifier = Modifier.width(20.dp),
            textAlign = TextAlign.Center,
            style = Typography.bodySmall
        )

        IconButton(onClick = onIncrease) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowUp,
                contentDescription = "Increase quantity"
            )
        }
    }
}

// Preview of the QuantityChooser
@Preview(showBackground = true)
@Composable
fun QuantityChooserPreview() {
    QuantityChooser(
        quantity = 1,
        onIncrease = { /*TODO*/ },
        onDecrease = { /*TODO*/ }
    )
}