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

// Main scaffold that includes the top bar, bottom navigation, and action icons
@Composable
fun MainScaffold(
    navController: NavController,
    title: String = "MediVault",
    contentModifier: Modifier = Modifier,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    // Utilize MScaffold to structure the layout
    MScaffold(
        navController = navController,
        title = title,
        contentModifier = contentModifier,
        bottomBar = bottomBar,
        actions = {
            // Search action icon in the top bar
            IconButton(
                onClick = { navController.navigate(Screen.Search.route) },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(imageVector = Icons.Filled.Search, "Search")
            }
            // Cart action icon in the top bar
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
        // Content of the scaffold with applied padding
        content(paddingValues)
    }
}

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