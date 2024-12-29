package com.ensas.medivault.data.model

// To store the medication data
data class Medication (
    val id: String,
    val name: String,
    val description: String,
    val price: Double, // In MAD
    val contents: String,
    val quantity: Int = 0,
    val imageUrl: String = "https://phabcart.imgix.net/cdn/scdn/images/uploads/m0459_web.jpg?auto=compress&lossless=1&w=385",
    val usageInstructions: String,
    val manufacturer: String,
)