package com.kenneth_demo.data.model

import com.google.gson.annotations.SerializedName


 //This is the data model to receive the weather API response.
 //the WeatherResponse class stores the weather API's response
data class WeatherResponse(
    @SerializedName("coord")
    val coordinates: Coordinates?,
    
    @SerializedName("weather")
    val weather: List<Weather>?,
    
    @SerializedName("base")
    val base: String?,
    
    @SerializedName("main")
    val main: MainWeather?,
    
    @SerializedName("visibility")
    val visibility: Int?,
    
    @SerializedName("wind")
    val wind: Wind?,
    
    @SerializedName("clouds")
    val clouds: Clouds?,
    
    @SerializedName("dt")
    val dateTime: Long?,
    
    @SerializedName("sys")
    val system: SystemInfo?,
    
    @SerializedName("timezone")
    val timezone: Int?,
    
    @SerializedName("id")
    val id: Int?,
    
    @SerializedName("name")
    val name: String?,
    
    @SerializedName("cod")
    val cod: Int?
)


 //Stores the coordinates of the selected location after getting a api response
data class Coordinates(
    @SerializedName("lon")
    val longitude: Double?,
    
    @SerializedName("lat")
    val latitude: Double?
)

//Stores the weather conditions after receiving a api response
data class Weather(
    @SerializedName("id")
    val id: Int?,
    
    @SerializedName("main")
    val main: String?,
    
    @SerializedName("description")
    val description: String?,
    
    @SerializedName("icon")
    val icon: String?
)

//main weather parameters such as temperature and humidity
//this information is received from the Weather API response
data class MainWeather(
    @SerializedName("temp")
    val temperature: Double?,
    
    @SerializedName("feels_like")
    val feelsLike: Double?,
    
    @SerializedName("temp_min")
    val tempMin: Double?,
    
    @SerializedName("temp_max")
    val tempMax: Double?,
    
    @SerializedName("pressure")
    val pressure: Int?,
    
    @SerializedName("humidity")
    val humidity: Int?,
    
    @SerializedName("sea_level")
    val seaLevel: Int?,
    
    @SerializedName("grnd_level")
    val groundLevel: Int?
)

//Wind information for the selected area
data class Wind(
    @SerializedName("speed")
    val speed: Double?,
    
    @SerializedName("deg")
    val degree: Int?,
    
    @SerializedName("gust")
    val gust: Double?
)

//Cloud information for the selected area 
data class Clouds(
    @SerializedName("all")
    val all: Int?
)

/**
 * System information (country, sunrise, sunset, etc.).
 */

 //Information stored about the country, and sunrise/sunset details times
data class SystemInfo(
    @SerializedName("type")
    val type: Int?,
    
    @SerializedName("id")
    val id: Int?,
    
    @SerializedName("country")
    val country: String?,
    
    @SerializedName("sunrise")
    val sunrise: Long?,
    
    @SerializedName("sunset")
    val sunset: Long?
)

