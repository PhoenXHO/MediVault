package com.ensas.medivault.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ensas.medivault.data.model.Medication
import com.ensas.medivault.data.model.SearchFilter
import com.ensas.medivault.data.model.SortOption
import com.ensas.medivault.data.repository.FavoritesRepositoryInterface
import com.ensas.medivault.data.repository.MedicationRepository
import com.ensas.medivault.ui.components.FilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MedicationRepository,
    private val favoritesRepository: FavoritesRepositoryInterface // Inject FavoritesRepository
) : ViewModel() {
    private val _searchResults = MutableStateFlow<List<Medication>>(emptyList())
    val searchResults: StateFlow<List<Medication>> = _searchResults

    private val _filters = MutableStateFlow(SearchFilter())
    val filters: StateFlow<SearchFilter> = _filters

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    fun setFilters(filters: SearchFilter) {
        _filters.value = filters
        search()
    }

    private fun search() {
        viewModelScope.launch {
            var medications = repository.getMedications()

            if (filters.value.favoritesOnly) {
                val favoriteIds = favoritesRepository.getFavorites().first()
                medications = medications.filter { favoriteIds.contains(it.id) }
            }

            medications = medications.filter { medication ->
                medication.price >= filters.value.minPrice &&
                medication.price <= filters.value.maxPrice &&
                (medication.name.contains(filters.value.query, ignoreCase = true) ||
                 medication.description.contains(filters.value.query, ignoreCase = true))
            }

            _searchResults.value = when (filters.value.sortBy) {
                SortOption.NAME -> medications.sortedBy { it.name }
                SortOption.PRICE_ASC -> medications.sortedBy { it.price }
                SortOption.PRICE_DESC -> medications.sortedByDescending { it.price }
            }
        }
    }

    fun removeFilter(type: FilterType) {
        when (type) {
            FilterType.QUERY -> {
                _filters.value = _filters.value.copy(query = "")
            }
            FilterType.PRICE_RANGE -> {
                _filters.value = _filters.value.copy(minPrice = 0.0, maxPrice = Double.MAX_VALUE)
            }
            FilterType.SORT_OPTION -> {
                _filters.value = _filters.value.copy(sortBy = SortOption.NAME)
            }
            FilterType.FAVORITES_ONLY -> {
                _filters.value = _filters.value.copy(favoritesOnly = false)
            }
        }
        // Trigger search with updated filters
        search()
    }
}