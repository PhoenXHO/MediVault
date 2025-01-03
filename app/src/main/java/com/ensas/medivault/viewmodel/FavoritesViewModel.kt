package com.ensas.medivault.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ensas.medivault.data.repository.FavoritesRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepositoryInterface
) : ViewModel() {
    // Add states for error messages
    private val _authError = MutableStateFlow<Int?>(null)
    val authError: StateFlow<Int?> get() = _authError

    // Add loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // Favorites state
    private val _favorites = MutableStateFlow<List<Int>>(emptyList())
    val favorites = _favorites.asStateFlow()

    fun initFavorites() {
        // Observe favorites from repository
        viewModelScope.launch {
            favoritesRepository.getFavorites().collect { favoriteList ->
                _favorites.value = favoriteList
            }
        }
    }

    // Add to favorites
    fun addToFavorites(medicationId: Int) {
        viewModelScope.launch {
            favoritesRepository.addFavorite(medicationId)
        }
    }

    // Remove from favorites
    fun removeFromFavorites(medicationId: Int) {
        viewModelScope.launch {
            favoritesRepository.removeFavorite(medicationId)
        }
    }
}