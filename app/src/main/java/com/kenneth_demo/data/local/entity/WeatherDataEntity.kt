package com.kenneth_demo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for caching weather data locally.
 * This allows offline access to recently viewed weather information.
 */
@Entity(tableName = "weather_data")
data class WeatherDataEntity(
    @PrimaryKey
    val cityName: String,
    
    val latitude: Double,
    
    val longitude: Double,
    
    val temperature: Double?,
    
    val feelsLike: Double?,
    
    val tempMin: Double?,
    
    val tempMax: Double?,
    
    val pressure: Int?,
    
    val humidity: Int?,
    
    val description: String?,
    
    val icon: String?,
    
    val windSpeed: Double?,
    
    val country: String?,
    
    val sunrise: Long?,
    
    val sunset: Long?,
    
    val lastUpdated: Long = System.currentTimeMillis()
)

