package com.ensas.medivault.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.data.model.Medication
import com.ensas.medivault.data.repository.FakeFavoritesRepository
import com.ensas.medivault.data.repository.FakeRepository
import com.ensas.medivault.ui.navigation.Screen
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.viewmodel.CartViewModel
import com.ensas.medivault.viewmodel.FavoritesViewModel
import com.ensas.medivault.viewmodel.MedicationsViewModel

@Composable
fun MedicationsList(
    medications: List<Medication>,
    state: LazyListState,
    navController: NavController,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel,
    modifier: Modifier = Modifier
) {
    // `LazyColumn` is a vertically scrolling grid that only composes and lays out the currently visible items
    // (Equivalent to using `RecyclerView` in Android)
    LazyColumn(
        state = state,
        modifier = modifier
            .fillMaxSize()
            .padding(Dimensions.paddingMedium),
        verticalArrangement = Arrangement.spacedBy(Dimensions.marginMedium),
        userScrollEnabled = true
    ) {
        items(medications) { medication ->
            MedicationItem(
                medication = medication,
                cartViewModel = cartViewModel,
                favoritesViewModel = favoritesViewModel,
                onItemClick = { medicationId ->
                    navController.navigate(
                        Screen.MedicationDetails.createRoute(medicationId.toString())
                    )
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MedicationsListPreview() {
    val medications by MedicationsViewModel(FakeRepository()).medications.collectAsState()
    MedicationsList(
        medications = medications,
        state = LazyListState(),
        navController = rememberNavController(),
        cartViewModel = CartViewModel(),
        favoritesViewModel = FavoritesViewModel(FakeFavoritesRepository())
    )
}