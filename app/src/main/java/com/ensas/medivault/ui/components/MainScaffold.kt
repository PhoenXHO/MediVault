package com.ensas.medivault.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.ui.navigation.Screen

// For the main scaffold component (which contains the top bar and the bottom navigation bar)
@Composable
fun MainScaffold(
    navController: NavController,
    contentModifier: Modifier = Modifier,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    MScaffold(
        navController = navController,
        title = "MediVault",
        content = content,
        contentModifier = contentModifier,
        bottomBar = bottomBar,
        actions = {
            IconButton(onClick = { navController.navigate(Screen.Search.route) }) {
                Icon(imageVector = Icons.Filled.Search, "Search")
            }
            IconButton(onClick = { navController.navigate(Screen.Cart.route) }) {
                Icon(imageVector = Icons.Filled.ShoppingCart, "Cart")
            }
        }
    )
}

// Preview the MainScaffold component
@Preview(showBackground = true)
@Composable
fun MainScaffoldPreview() {
    MainScaffold(
        navController = rememberNavController(),
        contentModifier = Modifier.padding(16.dp)
    ) {
        Text("Content - Main Scaffold")
    }
}