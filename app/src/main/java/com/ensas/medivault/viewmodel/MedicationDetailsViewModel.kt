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
    private val _medication = MutableStateFlow<Medication?>(null)
    val medication: StateFlow<Medication?> get() = _medication

    fun fetchMedicationDetails(medicationId: Int) {
        // Fetch the medication details from the repository in a coroutine
        // We are using viewModelScope to launch the coroutine in the context of ViewModel
        // so that it gets automatically canceled when the ViewModel is cleared
        viewModelScope.launch {
            _medication.value = repository.getMedicationById(medicationId)
        }
    }
}