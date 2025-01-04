package com.ensas.medivault.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.ui.theme.MediVaultTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MScaffold(
    navController: NavController,
    title: String,
    backArrow: Boolean = false,
    contentModifier: Modifier = Modifier,
    bottomBarModifier: Modifier = Modifier,
    bottomBar: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    // Scaffold provides the basic layout structure with top and bottom bars
    Scaffold(
        // TopAppBar displays the title and optional action icons
        topBar = {
            TopAppBar(
                title = { Text(title, color = MaterialTheme.colorScheme.onSurface) },
                actions = actions,
                navigationIcon = {
                    if (backArrow) {
                        // Display back arrow if enabled
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                        }
                    }
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = Color.Black,
                )
            )
        },
        // Bottom bar with optional modifiers and content
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .then(bottomBarModifier),
                tonalElevation = 0.dp,
                color = Color.Transparent
            ) {
                bottomBar()
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        // Content of the scaffold with applied padding
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .then(contentModifier),
        ) { content(paddingValues) }
    }
}

@Preview(showBackground = true)
@Composable
fun MScaffoldPreview() {
    MediVaultTheme {
        MScaffold(
            navController = rememberNavController(),
            title = "Title",
            backArrow = true,
            contentModifier = Modifier.padding(16.dp),
            actions = {
                IconButton(onClick = { /* Handle action */ }) {
                    Icon(Icons.Default.FilterList, "Filter")
                }
            }
        ) { Text("Content - MScaffold") }
    }
}