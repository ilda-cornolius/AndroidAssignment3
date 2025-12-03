package com.kenneth_demo.data.local.dao

import androidx.room.*
import com.kenneth_demo.data.local.entity.WeatherDataEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for weather data caching.
 * Provides methods for storing and retrieving cached weather data.
 */
@Dao
interface WeatherDataDao {
    
    /**
     * Retrieves cached weather data for a specific city.
     * 
     * @param cityName The name of the city
     * @return Flow of WeatherDataEntity if found
     */
    @Query("SELECT * FROM weather_data WHERE cityName = :cityName LIMIT 1")
    fun getWeatherData(cityName: String): Flow<WeatherDataEntity?>
    
    /**
     * Retrieves cached weather data for a specific city (one-time read).
     * 
     * @param cityName The name of the city
     * @return WeatherDataEntity if found, null otherwise
     */
    @Query("SELECT * FROM weather_data WHERE cityName = :cityName LIMIT 1")
    suspend fun getWeatherDataSync(cityName: String): WeatherDataEntity?
    
    /**
     * Retrieves all cached weather data.
     * 
     * @return Flow of list of WeatherDataEntity
     */
    @Query("SELECT * FROM weather_data ORDER BY lastUpdated DESC")
    fun getAllWeatherData(): Flow<List<WeatherDataEntity>>
    
    /**
     * Inserts or updates weather data.
     * 
     * @param weatherData The weather data to insert or update
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateWeatherData(weatherData: WeatherDataEntity)
    
    /**
     * Deletes weather data for a specific city.
     * 
     * @param cityName The name of the city
     */
    @Query("DELETE FROM weather_data WHERE cityName = :cityName")
    suspend fun deleteWeatherData(cityName: String)
    
    /**
     * Deletes all cached weather data.
     */
    @Query("DELETE FROM weather_data")
    suspend fun deleteAllWeatherData()
    
    /**
     * Deletes weather data older than the specified timestamp.
     * Useful for cache cleanup.
     * 
     * @param timestamp The timestamp threshold (data older than this will be deleted)
     */
    @Query("DELETE FROM weather_data WHERE lastUpdated < :timestamp")
    suspend fun deleteOldWeatherData(timestamp: Long)
}

