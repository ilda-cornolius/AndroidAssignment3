package com.kenneth_demo

import android.app.Application
import com.kenneth_demo.data.local.WeatherDatabase
import com.kenneth_demo.data.network.RetrofitClient
import com.kenneth_demo.data.repository.WeatherRepository

/**
 * Application class for dependency injection.
 * Provides access to repository and database instances throughout the app.
 */
class WeatherApplication : Application() {
    
    // Database instance
    val database: WeatherDatabase by lazy {
        WeatherDatabase.getDatabase(this)
    }
    
    // Repository instance
    val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(
            weatherApiService = RetrofitClient.weatherApiService,
            favoriteLocationDao = database.favoriteLocationDao(),
            weatherDataDao = database.weatherDataDao()
        )
    }
}

