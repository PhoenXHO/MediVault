package com.ensas.medivault.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ensas.medivault.data.model.Medication
import com.ensas.medivault.data.repository.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationDetailsViewModel @Inject constructor(private val repository: MedicationRepository) : ViewModel() {
    // StateFlow to hold the details of the selected medication
    private val _medication = MutableStateFlow<Medication?>(null)
    val medication: StateFlow<Medication?> get() = _medication

    // Fetches the medication details by ID
    fun fetchMedicationDetails(medicationId: Int) {
        // Launch a coroutine within the ViewModel's scope
        viewModelScope.launch {
            // Retrieve medication details from the repository and update the state
            _medication.value = repository.getMedicationById(medicationId)
        }
    }
}