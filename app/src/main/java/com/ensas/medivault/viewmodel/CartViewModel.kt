package com.ensas.medivault.viewmodel

import androidx.lifecycle.ViewModel
import com.ensas.medivault.data.model.Medication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<Medication>>(emptyList())
    val cartItems: StateFlow<List<Medication>> = _cartItems

    fun addToCart(medication: Medication) {
        if (_cartItems.value.any { it.id == medication.id }) {
            updateQuantity(medication.id, getQuantity(medication.id) + 1)
        } else {
            _cartItems.value += medication.copy(quantity = 1)
        }
    }

    fun removeFromCart(id: Int) {
        _cartItems.value = _cartItems.value.filter { it.id != id }
    }

    fun updateQuantity(id: Int, quantity: Int) {
        _cartItems.value = _cartItems.value.map {
            if (it.id == id) {
                it.copy(quantity = quantity)
            } else {
                it
            }
        }
    }

    private fun getQuantity(id: Int): Int {
        return _cartItems.value.find { it.id == id }?.quantity ?: 0
    }
}