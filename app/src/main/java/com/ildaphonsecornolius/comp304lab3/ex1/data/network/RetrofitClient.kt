package com.ildaphonsecornolius.comp304lab3.ex1.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


 //This object sets up the API service instance of the WeatherAPI service
 //It also handles the network configuration to access the API
 //It also handles response/request http logging
object RetrofitClient {
    
    
     //the base url for the api
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    
    //The API key used in the base url to make a request
    const val API_KEY = "1e4444cf0ec61bfcebccf8038ce2ebde"
    
   
     //Creates an OkHttp Client Instance to log network requests and responses
     //a OkHttp client is needed when using Retrofit for making API calls 
    private fun createOkHttpClient(): OkHttpClient {

        //creating a logging interceptor to be included in the OkHttp client
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        //Creates an instance of the OkHttpClient
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
     //Function to create a Retrofit Http client to handle making API calls 
     //Only uses the function when called due to the lazy keyword
    private val retrofit: Retrofit by lazy {
        //Creates a RetroFit Client instance
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
   //function to create a weatherAPIService instance
   //only creates the function when called using lazy
    val weatherApiService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}

