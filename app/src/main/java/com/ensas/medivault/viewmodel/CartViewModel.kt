package com.ensas.medivault.viewmodel

import androidx.lifecycle.ViewModel
import com.ensas.medivault.data.model.Medication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {
    // StateFlow to hold the list of items in the cart
    private val _cartItems = MutableStateFlow<List<Medication>>(emptyList())
    val cartItems: StateFlow<List<Medication>> = _cartItems

    // Function to add a medication to the cart
    fun addToCart(medication: Medication) {
        if (_cartItems.value.any { it.id == medication.id }) {
            // If the medication is already in the cart, increase its quantity
            updateQuantity(medication.id, getQuantity(medication.id) + 1)
        } else {
            // Add the medication to the cart with a quantity of 1
            _cartItems.value += medication.copy(quantity = 1)
        }
    }

    // Function to remove a medication from the cart by its ID
    fun removeFromCart(id: Int) {
        _cartItems.value = _cartItems.value.filter { it.id != id }
    }

    // Function to update the quantity of a specific medication in the cart
    fun updateQuantity(id: Int, quantity: Int) {
        _cartItems.value = _cartItems.value.map {
            if (it.id == id) {
                it.copy(quantity = quantity)
            } else {
                it
            }
        }
    }

    // Helper function to get the current quantity of a medication in the cart
    private fun getQuantity(id: Int): Int {
        return _cartItems.value.find { it.id == id }?.quantity ?: 0
    }
}