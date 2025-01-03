package com.ensas.medivault.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ensas.medivault.ui.theme.CustomShapes
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.MediVaultTheme
import com.ensas.medivault.ui.theme.Typography

@Composable
fun MTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier,
    shape: Shape = CustomShapes.RoundedCornerShape,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        if (label != null) {
            Text(
                text = label,
                style = Typography.displaySmall,
                modifier = Modifier
                    .padding(
                        horizontal = Dimensions.paddingLarge,
                        vertical = Dimensions.paddingSmall
                    )
            )
        }

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused }
                .border(
                    width = 2.dp,
                    color = if (isFocused) MaterialTheme.colorScheme.primary
                        else Color.Transparent,
                    shape = shape
                ),
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            shape = shape,
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation()
                else VisualTransformation.None,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,

                cursorColor = MaterialTheme.colorScheme.primary,
            ),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            keyboardOptions = keyboardOptions,
        )
    }
}

@Preview
@Composable
fun MTextFieldPreview() {
    MediVaultTheme {
        MTextField(
            value = "",
            onValueChange = {},
            placeholder = "Enter your email",
        )
    }
}