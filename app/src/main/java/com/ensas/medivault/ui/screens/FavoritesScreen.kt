package com.ensas.medivault.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ensas.medivault.data.repository.FakeFavoritesRepository
import com.ensas.medivault.data.repository.FakeRepository
import com.ensas.medivault.ui.components.MainScaffold
import com.ensas.medivault.ui.components.MBottomBar
import com.ensas.medivault.ui.components.MedicationsList
import com.ensas.medivault.ui.navigation.Screen
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.MediVaultTheme
import com.ensas.medivault.viewmodel.CartViewModel
import com.ensas.medivault.viewmodel.FavoritesViewModel
import com.ensas.medivault.viewmodel.MedicationsViewModel

@Composable
fun FavoritesScreen(
    navController: NavController,
    cartViewModel: CartViewModel,
    medicationsViewModel: MedicationsViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel
) {
    // Collect favorites from the view model
    val favorites by favoritesViewModel.favorites.collectAsState()
    // Collect all medications to filter favorites
    val allMedications by medicationsViewModel.medications.collectAsState()
    // Filter medications that are marked as favorites
    val favoriteMedications = allMedications.filter { favorites.contains(it.id) }

    // Remember the scroll state for the favorites list
    val state = rememberLazyListState()

    MainScaffold(
        title = "Favorites",
        navController = navController,
        contentModifier = Modifier.padding(horizontal = Dimensions.paddingLarge),
        bottomBar = {
            // Bottom navigation bar
            MBottomBar(
                navController = navController,
                currentScreen = Screen.Favorites,
                modifier = Modifier
                    .padding(horizontal = Dimensions.paddingMedium)
                    .padding(bottom = Dimensions.paddingLarge)
                    .padding(bottom = Dimensions.paddingSmall),
            )
        }
    ) {
        // Display the list of favorite medications
        MedicationsList(
            medications = favoriteMedications,
            state = state,
            navController = navController,
            cartViewModel = cartViewModel,
            favoritesViewModel = favoritesViewModel
        )
    }
}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    addToFavorites: () -> Unit,
    removeFromFavorites: () -> Unit
) {
    // Button to add or remove medication from favorites
    IconButton(
        modifier = modifier,
        onClick = {
            if (isFavorite) {
                removeFromFavorites()
            } else {
                addToFavorites()
            }
        }
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite
                else Icons.Outlined.FavoriteBorder,
            contentDescription = if (isFavorite) "Remove from favorites"
                else "Add to favorites",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    MediVaultTheme {
        FavoritesScreen(
            navController = androidx.navigation.compose.rememberNavController(),
            cartViewModel = CartViewModel(),
            medicationsViewModel = MedicationsViewModel(FakeRepository()),
            favoritesViewModel = FavoritesViewModel(FakeFavoritesRepository())
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteButtonPreview() {
    MediVaultTheme {
        FavoriteButton(
            isFavorite = false,
            addToFavorites = {},
            removeFromFavorites = {}
        )
    }
}