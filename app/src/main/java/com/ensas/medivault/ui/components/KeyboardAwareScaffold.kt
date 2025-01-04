package com.ensas.medivault.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import com.ensas.medivault.ui.theme.Dimensions

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KeyboardAwareScaffold(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    val density = LocalDensity.current
    val imeVisible = WindowInsets.isImeVisible
    val imeHeight = WindowInsets.ime.getBottom(density)
    val maxOffset = with(density) {
        (LocalConfiguration.current.screenHeightDp * 0.6f).toDp()
    }

    // Scaffold that adjusts the snackbar position based on keyboard visibility
    Scaffold(
        modifier = modifier,
        snackbarHost = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = if (imeVisible) (-imeHeight.dp).coerceAtLeast(-maxOffset) else 0.dp)
                    .padding(bottom = Dimensions.paddingMedium)
            ) {
                SnackbarHost(hostState = snackbarHostState)
            }
        }
    ) { paddingValues ->
        // Content of the scaffold with applied padding
        content(paddingValues)
    }
}