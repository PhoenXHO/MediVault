package com.ensas.medivault.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medications")
data class Medication(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val price: Double, // In MAD
    val contents: String,
    val quantity: Int = 0,
    val imageUrl: String = "https://phabcart.imgix.net/cdn/scdn/images/uploads/m0459_web.jpg?auto=compress&lossless=1&w=385",
    val usageInstructions: String,
    val manufacturer: String,
)