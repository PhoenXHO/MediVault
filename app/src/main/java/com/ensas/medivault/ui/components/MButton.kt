package com.ensas.medivault.ui.components

import androidx.compose.foundation.border
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
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
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