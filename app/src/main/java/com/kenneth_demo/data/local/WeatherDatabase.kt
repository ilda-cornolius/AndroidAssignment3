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
@Database(
    entities = [FavoriteLocation::class, WeatherDataEntity::class],
    version = 2,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    
    /**
     * Provides access to FavoriteLocationDao for favorite location operations.
     */
    abstract fun favoriteLocationDao(): FavoriteLocationDao
    
    /**
     * Provides access to WeatherDataDao for weather data caching operations.
     */
    abstract fun weatherDataDao(): WeatherDataDao
    
    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null
        
        /**
         * Gets the singleton instance of WeatherDatabase.
         * Creates the database if it doesn't exist yet.
         * 
         * @param context The application context
         * @return The WeatherDatabase instance
         */
        fun getDatabase(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
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

