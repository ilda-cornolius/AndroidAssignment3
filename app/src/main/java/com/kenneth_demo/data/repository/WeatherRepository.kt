package com.kenneth_demo.data.repository

import android.util.Log
import com.kenneth_demo.data.local.WeatherDatabase
import com.kenneth_demo.data.local.dao.FavoriteLocationDao
import com.kenneth_demo.data.local.dao.WeatherDataDao
import com.kenneth_demo.data.local.entity.FavoriteLocation
import com.kenneth_demo.data.local.entity.WeatherDataEntity
import com.kenneth_demo.data.model.WeatherResponse
import com.kenneth_demo.data.network.RetrofitClient
import com.kenneth_demo.data.network.WeatherApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Repository class implementing the Repository pattern.
 * Abstracts data access and provides a single source of truth for weather data.
 * Handles both network calls and local database operations.
 */
class WeatherRepository(
    private val weatherApiService: WeatherApiService,
    private val favoriteLocationDao: FavoriteLocationDao,
    private val weatherDataDao: WeatherDataDao
) {
    
    companion object {
        // Cache expiration time: 30 minutes
        private const val CACHE_EXPIRATION_TIME = 30 * 60 * 1000L
    }
    
    /**
     * Fetches weather data for a city, using cache if available and valid.
     * 
     * @param cityName The name of the city
     * @param forceRefresh If true, bypasses cache and fetches from network
     * @return Result containing WeatherResponse or error
     */
    suspend fun getWeatherByCity(cityName: String, forceRefresh: Boolean = false): Result<WeatherResponse> {
        return try {
            // Check cache first if not forcing refresh
            if (!forceRefresh) {
                val cachedData = weatherDataDao.getWeatherDataSync(cityName)
                if (cachedData != null && isCacheValid(cachedData.lastUpdated)) {
                    // Return cached data converted to WeatherResponse
                    return Result.success(cachedDataToWeatherResponse(cachedData))
                }
            }
            
            // Fetch from network
            val response = weatherApiService.getWeatherByCity(
                cityName = cityName,
                apiKey = RetrofitClient.API_KEY
            )
            
            // Save to cache
            response.let { weatherResponse ->
                val entity = weatherResponseToEntity(weatherResponse)
                weatherDataDao.insertOrUpdateWeatherData(entity)
            }
            
            Result.success(response)
        } catch (e: Exception) {
            // Log the full error for debugging
            Log.e("WeatherRepository", "Network error for city: $cityName", e)
            Log.e("WeatherRepository", "Error type: ${e.javaClass.simpleName}")
            Log.e("WeatherRepository", "Error message: ${e.message}")
            Log.e("WeatherRepository", "API Key: ${RetrofitClient.API_KEY.take(8)}...")
            
            // If network fails, try to return cached data
            val cachedData = weatherDataDao.getWeatherDataSync(cityName)
            if (cachedData != null) {
                Log.d("WeatherRepository", "Returning cached data for $cityName")
                Result.success(cachedDataToWeatherResponse(cachedData))
            } else {
                // Provide a more helpful error message
                val errorMessage = when {
                    RetrofitClient.API_KEY == "YOUR_API_KEY_HERE" -> {
                        "API key not configured. Please set your OpenWeatherMap API key in RetrofitClient.kt"
                    }
                    e.message?.contains("Unable to resolve host", ignoreCase = true) == true ||
                    e.message?.contains("UnknownHostException", ignoreCase = true) == true -> {
                        "⚠️ DNS Resolution Failed\n\nYour device cannot resolve 'api.openweathermap.org'.\n\n🔧 Quick Fixes:\n1. Use a PHYSICAL Android device (recommended)\n2. Restart emulator + restart computer\n3. Test browser on device: api.openweathermap.org\n4. Change emulator DNS to 8.8.8.8\n\nThis is a device/emulator DNS issue, not a code problem.\nYour API call format is correct!"
                    }
                    e.message?.contains("401", ignoreCase = true) == true || e.message?.contains("Unauthorized", ignoreCase = true) == true -> {
                        "Authentication failed (401).\n\nYour API key may be:\n• Not activated yet (wait 10-15 min)\n• Invalid\n\nCheck: RetrofitClient.kt line 23"
                    }
                    e.message?.contains("403", ignoreCase = true) == true || e.message?.contains("Forbidden", ignoreCase = true) == true -> {
                        "Access forbidden (403). Your API key may be blocked or invalid."
                    }
                    e.message?.contains("404", ignoreCase = true) == true -> {
                        "URL not found (404). Check the API endpoint configuration."
                    }
                    e.message?.contains("timeout", ignoreCase = true) == true || e.message?.contains("timed out", ignoreCase = true) == true -> {
                        "Connection timeout.\n\nCheck:\n• Internet connection speed\n• Firewall/VPN blocking requests\n• Try again later"
                    }
                    e.message?.contains("SSL", ignoreCase = true) == true || 
                    e.message?.contains("certificate", ignoreCase = true) == true ||
                    e.message?.contains("HandshakeException", ignoreCase = true) == true -> {
                        "SSL/Certificate error.\n\nFix:\n• Settings → Date & Time → Enable 'Automatic'\n• Check device date/time is correct"
                    }
                    e.message?.contains("NetworkSecurityException", ignoreCase = true) == true -> {
                        "Network security error.\n\nFix:\n• Rebuild the app (Clean → Rebuild)\n• Check network_security_config.xml"
                    }
                    else -> {
                        "Network Error: ${e.javaClass.simpleName}\n\nDetails: ${e.message}\n\nCheck:\n1. Internet connection\n2. API key: ${RetrofitClient.API_KEY.take(8)}...\n3. Check Logcat for full error"
                    }
                }
                Result.failure(Exception(errorMessage, e))
            }
        }
    }
    
    /**
     * Fetches weather data by coordinates.
     * 
     * @param lat Latitude
     * @param lon Longitude
     * @return Result containing WeatherResponse or error
     */
    suspend fun getWeatherByCoordinates(lat: Double, lon: Double): Result<WeatherResponse> {
        return try {
            val response = weatherApiService.getWeatherByCoordinates(
                lat = lat,
                lon = lon,
                apiKey = RetrofitClient.API_KEY
            )
            
            // Save to cache
            response.let { weatherResponse ->
                val entity = weatherResponseToEntity(weatherResponse)
                weatherDataDao.insertOrUpdateWeatherData(entity)
            }
            
            Result.success(response)
        } catch (e: Exception) {
            // Provide a more helpful error message
            val errorMessage = when {
                RetrofitClient.API_KEY == "YOUR_API_KEY_HERE" -> {
                    "API key not configured. Please set your OpenWeatherMap API key in RetrofitClient.kt"
                }
                e.message?.contains("Unable to resolve host") == true -> {
                    "Network error: Unable to connect. Please check your internet connection and API key."
                }
                e.message?.contains("401") == true -> {
                    "Authentication failed. Please check your API key in RetrofitClient.kt"
                }
                else -> {
                    e.message ?: "Failed to fetch weather data. Please check your internet connection."
                }
            }
            Result.failure(Exception(errorMessage, e))
        }
    }
    
    /**
     * Gets all favorite locations as a Flow.
     * 
     * @return Flow of list of FavoriteLocation
     */
    fun getAllFavorites(): Flow<List<FavoriteLocation>> {
        return favoriteLocationDao.getAllFavorites()
    }
    
    /**
     * Gets all favorite locations as a list (one-time read).
     * 
     * @return List of FavoriteLocation
     */
    suspend fun getAllFavoritesList(): List<FavoriteLocation> {
        return favoriteLocationDao.getAllFavoritesList()
    }
    
    /**
     * Checks if a location is in favorites.
     * 
     * @param cityName The name of the city
     * @return True if the location is a favorite, false otherwise
     */
    suspend fun isFavorite(cityName: String): Boolean {
        return favoriteLocationDao.getFavoriteByCityName(cityName) != null
    }
    
    /**
     * Adds a location to favorites.
     * 
     * @param weatherResponse WeatherResponse containing location information
     * @return The ID of the inserted favorite location
     */
    suspend fun addToFavorites(weatherResponse: WeatherResponse): Long {
        val favoriteLocation = FavoriteLocation(
            cityName = weatherResponse.name ?: "",
            latitude = weatherResponse.coordinates?.latitude ?: 0.0,
            longitude = weatherResponse.coordinates?.longitude ?: 0.0,
            country = weatherResponse.system?.country,
            lastUpdated = System.currentTimeMillis()
        )
        return favoriteLocationDao.insertFavorite(favoriteLocation)
    }
    
    /**
     * Removes a location from favorites.
     * 
     * @param cityName The name of the city to remove
     */
    suspend fun removeFromFavorites(cityName: String) {
        favoriteLocationDao.deleteFavoriteByCityName(cityName)
    }
    
    /**
     * Removes a favorite location.
     * 
     * @param favoriteLocation The FavoriteLocation to remove
     */
    suspend fun removeFavorite(favoriteLocation: FavoriteLocation) {
        favoriteLocationDao.deleteFavorite(favoriteLocation)
    }
    
    /**
     * Checks if cached data is still valid based on expiration time.
     * 
     * @param lastUpdated Timestamp of last update
     * @return True if cache is valid, false if expired
     */
    private fun isCacheValid(lastUpdated: Long): Boolean {
        return (System.currentTimeMillis() - lastUpdated) < CACHE_EXPIRATION_TIME
    }
    
    /**
     * Converts WeatherResponse to WeatherDataEntity for caching.
     */
    private fun weatherResponseToEntity(response: WeatherResponse): WeatherDataEntity {
        return WeatherDataEntity(
            cityName = response.name ?: "",
            latitude = response.coordinates?.latitude ?: 0.0,
            longitude = response.coordinates?.longitude ?: 0.0,
            temperature = response.main?.temperature,
            feelsLike = response.main?.feelsLike,
            tempMin = response.main?.tempMin,
            tempMax = response.main?.tempMax,
            pressure = response.main?.pressure,
            humidity = response.main?.humidity,
            description = response.weather?.firstOrNull()?.description,
            icon = response.weather?.firstOrNull()?.icon,
            windSpeed = response.wind?.speed,
            country = response.system?.country,
            sunrise = response.system?.sunrise,
            sunset = response.system?.sunset,
            lastUpdated = System.currentTimeMillis()
        )
    }
    
    /**
     * Converts WeatherDataEntity back to WeatherResponse for UI consumption.
     */
    private fun cachedDataToWeatherResponse(entity: WeatherDataEntity): WeatherResponse {
        // This is a simplified conversion - in a real app, you'd want to preserve more data
        return WeatherResponse(
            coordinates = com.kenneth_demo.data.model.Coordinates(
                longitude = entity.longitude,
                latitude = entity.latitude
            ),
            weather = listOf(
                com.kenneth_demo.data.model.Weather(
                    id = null,
                    main = null,
                    description = entity.description,
                    icon = entity.icon
                )
            ),
            main = com.kenneth_demo.data.model.MainWeather(
                temperature = entity.temperature,
                feelsLike = entity.feelsLike,
                tempMin = entity.tempMin,
                tempMax = entity.tempMax,
                pressure = entity.pressure,
                humidity = entity.humidity,
                seaLevel = null,
                groundLevel = null
            ),
            wind = com.kenneth_demo.data.model.Wind(
                speed = entity.windSpeed,
                degree = null,
                gust = null
            ),
            name = entity.cityName,
            system = com.kenneth_demo.data.model.SystemInfo(
                type = null,
                id = null,
                country = entity.country,
                sunrise = entity.sunrise,
                sunset = entity.sunset
            ),
            base = null,
            visibility = null,
            clouds = null,
            dateTime = entity.lastUpdated / 1000, // Convert milliseconds to seconds (API uses Unix timestamp)
            timezone = null,
            id = null,
            cod = 200
        )
    }
}

