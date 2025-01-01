package com.ensas.medivault.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MButton(
    enabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier? = null,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(),
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
    MButton(
        onClick = { /*TODO*/ }
    ) {
        Text("Button")
    }
}