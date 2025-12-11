package com.ildaphonsecornolius.comp304lab3.ex1

import android.app.Application
import com.ildaphonsecornolius.comp304lab3.ex1.data.local.WeatherDatabase
import com.ildaphonsecornolius.comp304lab3.ex1.data.network.RetrofitClient
import com.ildaphonsecornolius.comp304lab3.ex1.data.repository.WeatherRepository


 //This class Initializes the repository and database instances to be used
 //while the application is open
class WeatherApplication : Application() {
    
    // Creating a Database instance
    val database: WeatherDatabase by lazy {
        WeatherDatabase.getDatabase(this)
    }
    
    // Creating a Repository instance
    val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(
            //Creating the weather api service
            weatherApiService = RetrofitClient.weatherApiService,
            //Setting up the favoriate location dao
            favoriteLocationDao = database.favoriteLocationDao(),
            //Setting up the weather data dao
            weatherDataDao = database.weatherDataDao()
        )
    }
}

