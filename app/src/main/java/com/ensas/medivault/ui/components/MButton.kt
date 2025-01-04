package com.ensas.medivault.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ensas.medivault.ui.theme.MediVaultTheme

@Composable
fun MButton(
    enabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    content: @Composable () -> Unit
) {
    // Custom button with predefined styling and optional modifiers
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.extraLarge,
        colors = colors,
        modifier = modifier ?: Modifier
            .fillMaxWidth()
            .height(40.dp),
        enabled = enabled
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun MButtonPreview() {
    MediVaultTheme {
        MButton(
            onClick = { }
        ) {
            Text("Button")
        }
    }
}