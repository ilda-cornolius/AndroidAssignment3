package com.kenneth_demo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity representing a favorite location saved by the user.
 * This entity stores basic location information for quick access.
 */
@Entity(tableName = "favorite_locations")
data class FavoriteLocation(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val cityName: String,
    
    val latitude: Double,
    
    val longitude: Double,
    
    val country: String?,
    
    val lastUpdated: Long = System.currentTimeMillis()
)

