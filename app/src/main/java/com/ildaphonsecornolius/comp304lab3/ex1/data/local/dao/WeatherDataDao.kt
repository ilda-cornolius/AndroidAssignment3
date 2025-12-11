package com.ildaphonsecornolius.comp304lab3.ex1.data.local.dao

import androidx.room.*
import com.ildaphonsecornolius.comp304lab3.ex1.data.local.entity.WeatherDataEntity

//This is the data access object for the weather_data table
@Dao
interface WeatherDataDao {
    
    //Query to select a weather_data entry based on the cityName entered by the user
    @Query("SELECT * FROM weather_data WHERE cityName = :cityName LIMIT 1")
    suspend fun getWeatherDataSync(cityName: String): WeatherDataEntity?
    
    
     //This method updates or inserts a new weather_data entry in the table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateWeatherData(weatherData: WeatherDataEntity)
}

