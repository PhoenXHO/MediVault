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
    // StateFlow to hold authentication error messages related to favorites
    private val _authError = MutableStateFlow<Int?>(null)
    val authError: StateFlow<Int?> get() = _authError

    // StateFlow to indicate loading state during favorite operations
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // StateFlow to hold the list of favorite medication IDs
    private val _favorites = MutableStateFlow<List<Int>>(emptyList())
    val favorites = _favorites.asStateFlow()

    // Function to initialize and observe favorites from the repository
    fun initFavorites() {
        viewModelScope.launch {
            favoritesRepository.getFavorites().collect { favoriteList ->
                _favorites.value = favoriteList
            }
        }
    }

    // Function to add a medication to favorites
    fun addToFavorites(medicationId: Int) {
        viewModelScope.launch {
            favoritesRepository.addFavorite(medicationId)
        }
    }

    // Function to remove a medication from favorites
    fun removeFromFavorites(medicationId: Int) {
        viewModelScope.launch {
            favoritesRepository.removeFavorite(medicationId)
        }
    }
}