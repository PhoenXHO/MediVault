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
class MedicationsViewModel @Inject constructor(private val repository: MedicationRepository) : ViewModel() {
    // A list of medications
    // MutableStateFlow is a type of Flow that allows you to change its value
    // A StateFlow is a type of Flow that represents a read-only state with a single updatable data value
    // A Flow is a type that can emit multiple values sequentially
    private val _medications = MutableStateFlow<List<Medication>>(emptyList())
    // Making the list of medications available as a StateFlow to make it observable and read-only (immutable)
    val medications: StateFlow<List<Medication>> get() = _medications

    init {
        // Fetch the list of medications from the repository in a coroutine
        // We are using viewModelScope to launch the coroutine in the context of ViewModel
        // so that it gets automatically canceled when the ViewModel is cleared
        viewModelScope.launch {
            _medications.value = repository.getMedications()
        }
    }
}