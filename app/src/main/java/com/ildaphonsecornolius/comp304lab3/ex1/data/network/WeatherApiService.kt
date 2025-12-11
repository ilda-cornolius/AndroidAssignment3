package com.ildaphonsecornolius.comp304lab3.ex1.data.network

import com.ildaphonsecornolius.comp304lab3.ex1.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query


 //This is the retroface client used for weather API calls
 //it is used to make network requests for weather data
interface WeatherApiService {
    
    
     //API request to fetch current weather data for a city
    @GET("weather")
    //The requests queries for the cityName the apiKey and the unit system (celcius or farenheit)
    suspend fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
    
   
     //API request to fetch weather data containing the longtitude and latitude of a city
    @GET("weather")
    suspend fun getWeatherByCoordinates(
        //Queries for the long and lat, api key, and the unit system type
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}

