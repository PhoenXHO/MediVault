package com.ensas.medivault.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.data.model.SearchFilter
import com.ensas.medivault.data.model.SortOption
import com.ensas.medivault.data.repository.FakeFavoritesRepository
import com.ensas.medivault.data.repository.FakeRepository
import com.ensas.medivault.ui.components.FilterBar
import com.ensas.medivault.ui.components.FilterDialog
import com.ensas.medivault.ui.components.MBottomBar
import com.ensas.medivault.ui.components.MScaffold
import com.ensas.medivault.ui.components.MedicationsList
import com.ensas.medivault.ui.navigation.Screen
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.MediVaultTheme
import com.ensas.medivault.viewmodel.CartViewModel
import com.ensas.medivault.viewmodel.FavoritesViewModel
import com.ensas.medivault.viewmodel.SearchViewModel

@Composable
fun SearchResultsScreen(
    navController: NavController,
    cartViewModel: CartViewModel,
    viewModel: SearchViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel
) {
    // Collect search results and filters from the view model
    val searchResults by viewModel.searchResults.collectAsState()
    val filters by viewModel.filters.collectAsState()
    var showFilterDialog by remember { mutableStateOf(false) }

    MScaffold(
        navController = navController,
        title = "Search Results",
        backArrow = true,
        actions = {
            // Button to open filter dialog
            IconButton(onClick = { showFilterDialog = true }) {
                Icon(Icons.Filled.FilterList, "Edit Filters")
            }
        },
        bottomBar = {
            // Bottom navigation bar
            MBottomBar(
                navController = navController,
                currentScreen = Screen.Home,
                modifier = Modifier
                    .padding(horizontal = Dimensions.paddingMedium)
                    .padding(bottom = Dimensions.paddingLarge)
                    .padding(bottom = Dimensions.paddingSmall),
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimensions.paddingLarge)
        ) {
            // Display active filters
            FilterBar(
                filter = filters,
                onRemoveFilter = { type -> viewModel.removeFilter(type) }
            )

            if (searchResults.isNotEmpty()) {
                // Show number of results found
                Text(
                    text = "Found " +
                            if (searchResults.size == 1) "1 result"
                            else "${searchResults.size} results",
                    modifier = Modifier.padding(vertical = Dimensions.paddingMedium)
                )
            }

            // Display search results or a message if empty
            if (searchResults.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No results found")
                }
            } else {
                // List of medications matching the search
                MedicationsList(
                    medications = searchResults,
                    state = rememberLazyListState(),
                    navController = navController,
                    cartViewModel = cartViewModel,
                    favoritesViewModel = favoritesViewModel,
                    bottomPadding = paddingValues.calculateBottomPadding()
                )
            }

            // Dialog for filtering search results
            if (showFilterDialog) {
                FilterDialog(
                    currentFilter = filters,
                    onApplyFilters = { newFilters ->
                        // Apply new filters and close dialog
                        viewModel.setFilters(newFilters)
                        showFilterDialog = false
                    },
                    onDismiss = { showFilterDialog = false }
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchResultsScreenPreview() {
    val searchViewModel = SearchViewModel(
        FakeRepository(),
        FakeFavoritesRepository()
    )
    searchViewModel.setFilters(
        SearchFilter(
            query = "Med",
            minPrice = 10.0,
            maxPrice = 50.0,
            sortBy = SortOption.PRICE_DESC
        )
    )

    MediVaultTheme {
        SearchResultsScreen(
            navController = rememberNavController(),
            cartViewModel = CartViewModel(),
            viewModel = searchViewModel,
            favoritesViewModel = FavoritesViewModel(FakeFavoritesRepository())
        )
    }
}

@Preview
@Composable
fun SearchResultsScreenDarkPreview() {
    val searchViewModel = SearchViewModel(
        FakeRepository(),
        FakeFavoritesRepository()
    )
    searchViewModel.setFilters(
        SearchFilter(
            query = "Med",
            minPrice = 10.0,
            maxPrice = 50.0,
            sortBy = SortOption.PRICE_DESC
        )
    )

    MediVaultTheme(darkTheme = true) {
        SearchResultsScreen(
            navController = rememberNavController(),
            cartViewModel = CartViewModel(),
            viewModel = searchViewModel,
            favoritesViewModel = FavoritesViewModel(FakeFavoritesRepository())
        )
    }
}