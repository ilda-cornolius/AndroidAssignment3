package com.ildaphonsecornolius.comp304lab3.ex1.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

//Blueprint for the favorite_location data table in the database
 //The table name is favorite_location
@Entity(tableName = "favorite_locations")
data class FavoriteLocation(
    //data values for the favorite_location per entry
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val cityName: String,
    
    val latitude: Double,
    
    val longitude: Double,
    
    val country: String?,
    
    val lastUpdated: Long = System.currentTimeMillis()
)

