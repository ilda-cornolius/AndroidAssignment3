package com.kenneth_demo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kenneth_demo.data.local.dao.FavoriteLocationDao
import com.kenneth_demo.data.local.dao.WeatherDataDao
import com.kenneth_demo.data.local.entity.FavoriteLocation
import com.kenneth_demo.data.local.entity.WeatherDataEntity

/**
 * Room database for the weather application.
 * Manages local storage of favorite locations and cached weather data.
 */


 //This is the class of the room database for the application
@Database(
    //the database contains two entities, favorite_location and weather_data
    entities = [FavoriteLocation::class, WeatherDataEntity::class],
    version = 2,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    
    /**
     * Provides access to FavoriteLocationDao for favorite location operations.
     */
     //Creates an instance of the FavoriteLocaltionDao to access its' methods
     //this is used to query the favorite_location database
    abstract fun favoriteLocationDao(): FavoriteLocationDao
    
    /**
     * Provides access to WeatherDataDao for weather data caching operations.
     */
     //Creates an instance of WeatherDataDao to access its' methods
     //this is used to query the weather_data table
    abstract fun weatherDataDao(): WeatherDataDao
    
    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null
        
       
         //This function creates an instance of the Weather Database
         //The Context parameter represents the state of the application
        fun getDatabase(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                //creating a database instance using the databaseBuilder method using room
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

