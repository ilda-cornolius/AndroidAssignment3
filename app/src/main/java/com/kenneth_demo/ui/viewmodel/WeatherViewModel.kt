package com.kenneth_demo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kenneth_demo.data.model.WeatherResponse
import com.kenneth_demo.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * UI state for weather data.
 */
sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val weather: WeatherResponse) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

/**
 * ViewModel for managing weather data and business logic.
 * Follows MVVM architecture pattern.
 */
class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    
    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()
    
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()
    
    /**
     * Fetches weather data for a specific city.
     * 
     * @param cityName The name of the city
     * @param forceRefresh If true, bypasses cache and fetches from network
     */
    fun fetchWeather(cityName: String, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            
            repository.getWeatherByCity(cityName, forceRefresh)
                .onSuccess { weatherResponse ->
                    _uiState.value = WeatherUiState.Success(weatherResponse)
                    // Check if location is in favorites
                    checkIfFavorite(weatherResponse.name ?: "")
                }
                .onFailure { exception ->
                    _uiState.value = WeatherUiState.Error(
                        exception.message ?: "Failed to fetch weather data"
                    )
                }
        }
    }
    
    /**
     * Fetches weather data by coordinates.
     * 
     * @param lat Latitude
     * @param lon Longitude
     */
    fun fetchWeatherByCoordinates(lat: Double, lon: Double) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            
            repository.getWeatherByCoordinates(lat, lon)
                .onSuccess { weatherResponse ->
                    _uiState.value = WeatherUiState.Success(weatherResponse)
                    checkIfFavorite(weatherResponse.name ?: "")
                }
                .onFailure { exception ->
                    _uiState.value = WeatherUiState.Error(
                        exception.message ?: "Failed to fetch weather data"
                    )
                }
        }
    }
    
    /**
     * Adds current location to favorites.
     */
    fun addToFavorites(weatherResponse: WeatherResponse) {
        viewModelScope.launch {
            try {
                repository.addToFavorites(weatherResponse)
                _isFavorite.value = true
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
    }
    
    /**
     * Removes current location from favorites.
     * 
     * @param cityName The name of the city to remove
     */
    fun removeFromFavorites(cityName: String) {
        viewModelScope.launch {
            try {
                repository.removeFromFavorites(cityName)
                _isFavorite.value = false
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
    }
    
    /**
     * Toggles favorite status for the current location.
     * 
     * @param weatherResponse The current weather response
     */
    fun toggleFavorite(weatherResponse: WeatherResponse) {
        viewModelScope.launch {
            val cityName = weatherResponse.name ?: ""
            if (_isFavorite.value) {
                removeFromFavorites(cityName)
            } else {
                addToFavorites(weatherResponse)
            }
        }
    }
    
    /**
     * Checks if a location is in favorites.
     * 
     * @param cityName The name of the city
     */
    private fun checkIfFavorite(cityName: String) {
        viewModelScope.launch {
            _isFavorite.value = repository.isFavorite(cityName)
        }
    }
}

/**
 * Factory for creating WeatherViewModel instances.
 */
class WeatherViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

