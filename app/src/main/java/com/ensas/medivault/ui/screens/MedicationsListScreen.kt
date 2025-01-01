package com.ensas.medivault.ui.screens

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
import com.ensas.medivault.data.InitialData
import com.ensas.medivault.data.dao.MedicationDao
import com.ensas.medivault.data.model.Medication
import com.ensas.medivault.data.repository.MedicationRepository
import com.ensas.medivault.ui.components.MainScaffold
import com.ensas.medivault.ui.components.MedicationsList
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

    MainScaffold(
        navController = navController,
        contentModifier = Modifier.padding(horizontal = Dimensions.paddingLarge)
    ) {
        MedicationsList(
            medications = medications,
            state = state,
            navController = navController,
            cartViewModel = cartViewModel
        )
    }
}

// Preview of the MedicationsListScreen
@Preview(showBackground = true)
@Composable
fun MedicationsListScreenPreview() {
    MedicationsListScreen(
        navController = rememberNavController(),
        cartViewModel = CartViewModel(),
        viewModel = MedicationsViewModel(FakeRepository())
    )
}

// Fake repository to provide data for preview
class FakeRepository : MedicationRepository(FakeDao()) {
    override suspend fun getMedications(): List<Medication> {
        return InitialData.medications
    }
}

// Fake DAO to provide data for preview
private class FakeDao : MedicationDao {
    override suspend fun getAllMedications(): List<Medication> {
        return InitialData.medications
    }

    override suspend fun getMedicationById(medicationId: String): Medication? {
        return InitialData.medications.find { it.id == medicationId }
    }

    override suspend fun insertMedications(medications: List<Medication>) {
        // Not required for preview
    }

    override suspend fun insertMedication(medication: Medication) {
        // Not required for preview
    }
}