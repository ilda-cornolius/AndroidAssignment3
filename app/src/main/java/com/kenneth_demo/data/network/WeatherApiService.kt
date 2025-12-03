package com.kenneth_demo.data.network

import com.kenneth_demo.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface for weather API calls.
 * This service handles network requests to fetch weather data.
 */
interface WeatherApiService {
    
    /**
     * Fetches current weather data for a specific city.
     * 
     * @param cityName The name of the city
     * @param apiKey The API key for authentication
     * @param units The unit system (metric, imperial, or kelvin)
     * @return WeatherResponse containing weather data
     */
    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
    
    /**
     * Fetches current weather data by latitude and longitude.
     * 
     * @param lat The latitude coordinate
     * @param lon The longitude coordinate
     * @param apiKey The API key for authentication
     * @param units The unit system (metric, imperial, or kelvin)
     * @return WeatherResponse containing weather data
     */
    @GET("weather")
    suspend fun getWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}

