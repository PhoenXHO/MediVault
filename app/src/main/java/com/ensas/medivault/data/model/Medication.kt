package com.ensas.medivault.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medications")
data class Medication(
    @PrimaryKey val id: String,
    val name: String,
    val description: String = "",
    val price: Double, // In MAD
    val contents: String,
    val quantity: Int = 0,
    val imageUrl: String = "https://ubuntupharmacy.com/wp-content/uploads/2023/05/Parcetamol.jpg",
    val usageInstructions: String = "",
    val manufacturer: String = ""
) {
    val totalPrice: Double
        get() = price * quantity
}