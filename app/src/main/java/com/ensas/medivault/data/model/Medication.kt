package com.ensas.medivault.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity representing a Medication in the Room database
@Entity(tableName = "medications")
data class Medication(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String = "",
    val price: Double, // Price in MAD
    val contents: String,
    val quantity: Int = 0,
    val imageUrl: String = "https://ubuntupharmacy.com/wp-content/uploads/2023/05/Parcetamol.jpg",
    val uses: String = "",
    val importantInfo: String = "",
    val sideEffects: String = "",
    val precautions: String = "",
    val dosage: String = "",
) {
    // Calculates the total price based on quantity
    val totalPrice: Double
        get() = price * quantity
}