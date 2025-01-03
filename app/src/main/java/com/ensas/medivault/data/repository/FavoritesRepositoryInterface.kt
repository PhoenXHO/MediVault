package com.ensas.medivault.data.repository

import kotlinx.coroutines.flow.Flow

interface FavoritesRepositoryInterface {
    fun getFavorites(): Flow<List<Int>>
    suspend fun addFavorite(medicationId: Int)
    suspend fun removeFavorite(medicationId: Int)
}