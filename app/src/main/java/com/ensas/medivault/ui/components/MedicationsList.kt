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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.data.model.Medication
import com.ensas.medivault.data.repository.FakeFavoritesRepository
import com.ensas.medivault.data.repository.FakeRepository
import com.ensas.medivault.ui.navigation.Screen
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.MediVaultTheme
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
    modifier: Modifier = Modifier,
    bottomPadding: Dp = 0.dp
) {
    // LazyColumn for efficient vertical scrolling of medication items
    LazyColumn(
        state = state,
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimensions.marginMedium),
        userScrollEnabled = true
    ) {
        items(medications) { medication ->
            // Composable representing each medication item
            MedicationItem(
                medication = medication,
                cartViewModel = cartViewModel,
                favoritesViewModel = favoritesViewModel,
                onItemClick = { medicationId ->
                    // Navigate to medication details screen on item click
                    navController.navigate(
                        Screen.MedicationDetails.createRoute(medicationId.toString())
                    )
                },
                modifier = if (medications.indexOf(medication) == medications.size - 1) {
                    Modifier
                        .padding(bottom = bottomPadding)
                        .padding(bottom = Dimensions.paddingMedium)
                } else {
                    Modifier
                }
            )
        }
    }
}

@Preview
@Composable
fun MedicationsListPreview() {
    val medications by MedicationsViewModel(FakeRepository()).medications.collectAsState()
    MediVaultTheme {
        MedicationsList(
            medications = medications,
            state = LazyListState(),
            navController = rememberNavController(),
            cartViewModel = CartViewModel(),
            favoritesViewModel = FavoritesViewModel(FakeFavoritesRepository())
        )
    }
}