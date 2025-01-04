package com.ensas.medivault.data.repository

import kotlinx.coroutines.flow.Flow

// Interface defining methods for managing favorite medications
interface FavoritesRepositoryInterface {
    // Retrieves a flow of favorite medication IDs
    fun getFavorites(): Flow<List<Int>>

    // Adds a medication ID to favorites
    suspend fun addFavorite(medicationId: Int)

    // Removes a medication ID from favorites
    suspend fun removeFavorite(medicationId: Int)
}