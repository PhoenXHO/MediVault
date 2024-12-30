package com.ensas.medivault.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.ui.components.MedicationItem
import com.ensas.medivault.ui.navigation.Screen
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.viewmodel.CartViewModel
import com.ensas.medivault.viewmodel.MedicationsViewModel

// For the screen that displays the list of medications
@Composable
fun MedicationsListScreen(navController: NavController,
                          cartViewModel: CartViewModel,
                          viewModel: MedicationsViewModel = hiltViewModel()) {
    // Get the list of medications from the view model
    // `collectAsState` is used to observe the state of the medications and recompose the UI when the state changes
    // (Equivalent to using `ObservableCollection` in .NET)
    val medications by viewModel.medications.collectAsState()

    // `rememberLazyGridState` is used to save the scroll state of the grid
    val state = rememberLazyListState()

    // `LazyColumn` is a vertically scrolling grid that only composes and lays out the currently visible items
    // (Equivalent to using `RecyclerView` in Android)
    LazyColumn(
        state = state,
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(Dimensions.paddingMedium),
        verticalArrangement = Arrangement.spacedBy(Dimensions.marginSmall),
        userScrollEnabled = true
    ) {
        items(medications) { medication ->
            MedicationItem(
                medication = medication,
                cartViewModel = cartViewModel,
                onItemClick = { medicationId ->
                    navController.navigate(
                        Screen.MedicationDetails.createRoute(medicationId)
                    )
                }
            )
        }
    }
}

// Preview of the MedicationsListScreen
@Preview(showBackground = true)
@Composable
fun MedicationsListScreenPreview() {
    MedicationsListScreen(navController = rememberNavController(), cartViewModel = hiltViewModel())
}