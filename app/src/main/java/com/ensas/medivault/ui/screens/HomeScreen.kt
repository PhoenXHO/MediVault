package com.ensas.medivault.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.data.repository.FakeFavoritesRepository
import com.ensas.medivault.data.repository.FakeRepository
import com.ensas.medivault.ui.components.MBottomBar
import com.ensas.medivault.ui.components.MainScaffold
import com.ensas.medivault.ui.components.MedicationsList
import com.ensas.medivault.ui.navigation.Screen
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.MediVaultTheme
import com.ensas.medivault.viewmodel.CartViewModel
import com.ensas.medivault.viewmodel.FavoritesViewModel
import com.ensas.medivault.viewmodel.MedicationsViewModel

// For the screen that displays the list of medications
@Composable
fun HomeScreen(
    navController: NavController,
    cartViewModel: CartViewModel,
    viewModel: MedicationsViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
) {
    // Collect list of medications from the view model
    val medications by viewModel.medications.collectAsState()

    // Remember the scroll state for the medications list
    val state = rememberLazyListState()

    // Initialize favorites from Firebase
    favoritesViewModel.initFavorites()

    MainScaffold(
        navController = navController,
        contentModifier = Modifier.padding(horizontal = Dimensions.paddingLarge),
        bottomBar = {
            // Bottom navigation bar
            MBottomBar(
                navController = navController,
                currentScreen = Screen.Home,
                modifier = Modifier
                    .padding(horizontal = Dimensions.paddingMedium)
                    .padding(bottom = Dimensions.paddingLarge)
                    .padding(bottom = Dimensions.paddingSmall)
            )
        }
    ) { paddingValues ->
        // Display the list of medications
        MedicationsList(
            medications = medications,
            state = state,
            navController = navController,
            cartViewModel = cartViewModel,
            favoritesViewModel = favoritesViewModel,
            bottomPadding = paddingValues.calculateBottomPadding()
        )
    }
}

@Preview
@Composable
fun MedicationsListScreenPreview() {
    MediVaultTheme {
        HomeScreen(
            navController = rememberNavController(),
            cartViewModel = CartViewModel(),
            favoritesViewModel = FavoritesViewModel(FakeFavoritesRepository()),
            viewModel = MedicationsViewModel(FakeRepository())
        )
    }
}

@Preview
@Composable
fun MedicationsListScreenDarkPreview() {
    MediVaultTheme(darkTheme = true) {
        HomeScreen(
            navController = rememberNavController(),
            cartViewModel = CartViewModel(),
            favoritesViewModel = FavoritesViewModel(FakeFavoritesRepository()),
            viewModel = MedicationsViewModel(FakeRepository())
        )
    }
}