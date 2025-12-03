package com.kenneth_demo.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit client singleton for creating API service instances.
 * Handles network configuration, logging, and error handling.
 */
object RetrofitClient {
    
    // Base URL for OpenWeatherMap Current Weather API
    // IMPORTANT: The correct URL is "api.openweathermap.org" (with "map" in the domain)
    // NOTE: One Call API 3.0 uses /data/3.0/onecall but requires coordinates and subscription
    // Current Weather API (/data/2.5/weather) works with city names and is free
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    
    // API Key - Get your free API key from https://openweathermap.org/api
    // In production, this should be stored securely (e.g., using BuildConfig or local.properties)
    const val API_KEY = "1e4444cf0ec61bfcebccf8038ce2ebde"
    
    /**
     * Creates and configures OkHttpClient with logging interceptor.
     * This helps in debugging network requests and responses.
     */
    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    /**
     * Creates Retrofit instance with Gson converter and OkHttpClient.
     */
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    /**
     * Provides WeatherApiService instance using lazy initialization.
     */
    val weatherApiService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}

