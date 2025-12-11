package com.ildaphonsecornolius.comp304lab3.ex1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ildaphonsecornolius.comp304lab3.ex1.data.local.entity.FavoriteLocation
import com.ildaphonsecornolius.comp304lab3.ex1.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


 //The view model is the bridge between the UI and data 
 //it holds the data from configuration changes 
 //this is the view model for the favorites_location table
class FavoritesViewModel(private val repository: WeatherRepository) : ViewModel() {
    
    private val _favorites = MutableStateFlow<List<FavoriteLocation>>(emptyList())
    val favorites: StateFlow<List<FavoriteLocation>> = _favorites.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadFavorites()
    }
    
    //function to load all the favorite entries in the repository
    private fun loadFavorites() {
        viewModelScope.launch {
            repository.getAllFavorites().collect { favoriteList ->
                _favorites.value = favoriteList
            }
        }
    }
    
 
     //function to remove a favorite location
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
    
   //function to remove a favorite entry by city name 
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


 //class to create FavoriteViewModel instances
class FavoritesViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

