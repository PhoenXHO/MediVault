package com.ensas.medivault.data.model

// To store the medication data
data class Medication (
    val id: String,
    val name: String,
    val description: String,
    val price: Double, // In MAD
    val imageUrl: String = "https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/android/android.png"
)