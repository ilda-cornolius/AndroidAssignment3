package com.kenneth_demo.data.local.dao

import androidx.room.*
import com.kenneth_demo.data.local.entity.FavoriteLocation
import kotlinx.coroutines.flow.Flow

//The dao contains queries and methods to retrieve data from the database
//This class provides methods to retreive data from the favorite_locations table
@Dao
interface FavoriteLocationDao {
    //Query to select all favorite_locations entries from the table
    //It is organized by cityName in ascending alphabetical order
    //The UI displays the table without needing to refresh the page using the Flow method
    @Query("SELECT * FROM favorite_locations ORDER BY cityName ASC")
    fun getAllFavorites(): Flow<List<FavoriteLocation>>
    
    //Queries to retrieve a specific favorite location based on
    //what cityName the user enters in the text box
    @Query("SELECT * FROM favorite_locations WHERE cityName = :cityName LIMIT 1")
    suspend fun getFavoriteByCityName(cityName: String): FavoriteLocation?
    
    //Method to insert a new favorite location entry in the favorite_location table 
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteLocation: FavoriteLocation): Long
    
    //Query to delete a favorite location from the table
    @Delete
    suspend fun deleteFavorite(favoriteLocation: FavoriteLocation)
    
    //Query to delete a favorite location by city name in the database
    @Query("DELETE FROM favorite_locations WHERE cityName = :cityName")
    suspend fun deleteFavoriteByCityName(cityName: String)
}

