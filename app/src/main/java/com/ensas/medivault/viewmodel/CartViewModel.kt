package com.ensas.medivault.viewmodel

import androidx.lifecycle.ViewModel
import com.ensas.medivault.data.model.Medication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<Medication>>(emptyList())
    val cartItems: StateFlow<List<Medication>> = _cartItems

    fun addToCart(medication: Medication) {
        _cartItems.value += medication
    }
}