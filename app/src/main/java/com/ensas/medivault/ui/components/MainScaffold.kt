package com.ensas.medivault.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.ui.navigation.Screen
import com.ensas.medivault.ui.theme.MediVaultTheme

// For the main scaffold component (which contains the top bar and the bottom navigation bar)
@Composable
fun MainScaffold(
    navController: NavController,
    title: String = "MediVault",
    contentModifier: Modifier = Modifier,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    MScaffold(
        navController = navController,
        title = title,
        contentModifier = contentModifier,
        bottomBar = bottomBar,
        actions = {
            IconButton(
                onClick = { navController.navigate(Screen.Search.route) },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(imageVector = Icons.Filled.Search, "Search")
            }
            IconButton(
                onClick = { navController.navigate(Screen.Cart.route) },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(imageVector = Icons.Filled.ShoppingCart, "Cart")
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}

// Preview the MainScaffold component
@Preview(showBackground = true)
@Composable
fun MainScaffoldPreview() {
    MediVaultTheme {
        MainScaffold(
            navController = rememberNavController(),
            contentModifier = Modifier.padding(16.dp)
        ) {
            Text("Content - Main Scaffold")
        }
    }
}