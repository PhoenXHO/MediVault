package com.ensas.medivault.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.ui.navigation.Screen
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.Shapes

@Composable
fun MBottomBar(
    navController: NavController,
    currentScreen: Screen,
    modifier: Modifier = Modifier
) {
    Card(
        shape = Shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.paddingMedium),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navigateTo(navController, Screen.Home, currentScreen) },
                modifier = Modifier.size(64.dp)
            ) {
                IconWithText(
                    icon = Icons.Outlined.Home,
                    altIcon = Icons.Default.Home,
                    text = "Home",
                    isSelected = currentScreen == Screen.Home
                )
            }
            IconButton(
                onClick = { /*navigateTo(navController, Screen.Favorites, currentScreen)*/ },
                modifier = Modifier.size(64.dp)
            ) {
                IconWithText(
                    icon = Icons.Rounded.FavoriteBorder,
                    altIcon = Icons.Default.Favorite,
                    text = "Favorites",
                    isSelected = false
                )
            }
            IconButton(
                onClick = { navigateTo(navController, Screen.Profile, currentScreen) },
                modifier = Modifier.size(64.dp)
            ) {
                IconWithText(
                    icon = Icons.Outlined.PersonOutline,
                    altIcon = Icons.Default.Person,
                    text = "Profile",
                    isSelected = currentScreen == Screen.Profile
                )
            }
        }
    }
}

private fun navigateTo(navController: NavController, screen: Screen, currentScreen: Screen) {
    if (currentScreen != screen) {
        navController.navigate(screen.route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}

@Composable
fun IconWithText(
    icon: ImageVector,
    altIcon: ImageVector,
    text: String,
    isSelected: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.height(48.dp)
    ) {
        if (isSelected) {
            Icon(
                imageVector = altIcon,
                contentDescription = text
            )
        } else {
            Icon(
                imageVector = icon,
                contentDescription = text
            )
        }

        Text(
            text = text,
            style = TextStyle(fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FloatingBottomBarPreview() {
    MBottomBar(
        navController = rememberNavController(),
        currentScreen = Screen.Home
    )
}

@Preview(showBackground = true)
@Composable
fun IconWithTextPreview() {
    IconWithText(
        icon = Icons.Outlined.Home,
        altIcon = Icons.Default.Home,
        text = "Home",
        isSelected = true
    )
}