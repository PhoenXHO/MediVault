package com.ensas.medivault.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.ui.theme.Dimensions

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
    content: @Composable () -> Unit
) {
    // Scaffold is a layout component that implements the basic material design visual structure
    // It provides a top bar, a bottom navigation bar, and a floating action button
    Scaffold(
        // The top bar is a toolbar that displays the title and actions for the current screen
        topBar = {
            TopAppBar(
                title = { Text(title) },
                actions = actions,
                navigationIcon = {
                    if (backArrow) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                        }
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .then(bottomBarModifier),
                tonalElevation = 0.dp
            ) {
                bottomBar()
            }
        }
    ) {
        // Column is a layout component that places its children in a vertical sequence
        paddingValues -> Column(
            modifier = Modifier
                .padding(paddingValues)
                .then(contentModifier)
        ) { content() }
    }
}

@Preview(showBackground = true)
@Composable
fun MScaffoldPreview() {
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