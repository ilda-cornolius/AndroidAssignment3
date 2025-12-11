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


 //this function loads the Weather UI state for weather data
sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val weather: WeatherResponse) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}


 //this is the viewmodel for the weather_data table
class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    
    //variable to check if weather data is loaded or not 
    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()
    
    //variable to track if the current city is favorited
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()
    
    
     //function to make a request for weather data for a specific city
    fun fetchWeather(cityName: String, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            
            //function to fetch weather data from the weather repository
            _uiState.value = WeatherUiState.Loading
            
            //function to get weather data entry by city
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
    
   
     //this function gets weather data by coordinates
    fun fetchWeatherByCoordinates(lat: Double, lon: Double) {
        viewModelScope.launch {
            //variable to fetch weather data from the weather repository
            //stores a success value if data is fetched
            //stores a error message if data is not fetched
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
    
    //function to add selected city to favorites
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
    
    
     //function to remove selected location from favorites
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
    
    //toggles the favorite status of a weather_data entry
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
    
    
     //function to check if a city is in favorites
    private fun checkIfFavorite(cityName: String) {
        viewModelScope.launch {
            _isFavorite.value = repository.isFavorite(cityName)
        }
    }
}


 //class to create a WeatherViewModel instance
class WeatherViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

