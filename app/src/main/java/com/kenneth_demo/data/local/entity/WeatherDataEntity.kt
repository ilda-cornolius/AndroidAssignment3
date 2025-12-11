package com.kenneth_demo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


 //Setting up the blueprint for the Weather Data Table
@Entity(tableName = "weather_data")
data class WeatherDataEntity(
    //Setting up the data values per weather data entry
    //CityName is the primary key for this Weather data table
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

