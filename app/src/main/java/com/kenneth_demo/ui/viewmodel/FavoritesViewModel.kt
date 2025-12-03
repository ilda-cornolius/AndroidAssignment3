package com.kenneth_demo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kenneth_demo.data.local.entity.FavoriteLocation
import com.kenneth_demo.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing favorite locations.
 * Follows MVVM architecture pattern.
 */
class FavoritesViewModel(private val repository: WeatherRepository) : ViewModel() {
    
    private val _favorites = MutableStateFlow<List<FavoriteLocation>>(emptyList())
    val favorites: StateFlow<List<FavoriteLocation>> = _favorites.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadFavorites()
    }
    
    /**
     * Loads all favorite locations from the repository.
     */
    private fun loadFavorites() {
        viewModelScope.launch {
            repository.getAllFavorites().collect { favoriteList ->
                _favorites.value = favoriteList
            }
        }
    }
    
    /**
     * Removes a favorite location.
     * 
     * @param favoriteLocation The favorite location to remove
     */
    fun removeFavorite(favoriteLocation: FavoriteLocation) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.removeFavorite(favoriteLocation)
            } catch (e: Exception) {
                // Handle error if needed
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Removes a favorite location by city name.
     * 
     * @param cityName The name of the city to remove
     */
    fun removeFavoriteByCityName(cityName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.removeFromFavorites(cityName)
            } catch (e: Exception) {
                // Handle error if needed
            } finally {
                _isLoading.value = false
            }
        }
    }
}

/**
 * Factory for creating FavoritesViewModel instances.
 */
class FavoritesViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

