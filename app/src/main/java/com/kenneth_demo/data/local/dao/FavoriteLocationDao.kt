package com.kenneth_demo.data.local.dao

import androidx.room.*
import com.kenneth_demo.data.local.entity.FavoriteLocation
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for favorite locations.
 * Provides methods for CRUD operations on favorite locations.
 */
@Dao
interface FavoriteLocationDao {
    
    /**
     * Retrieves all favorite locations as a Flow for reactive updates.
     * 
     * @return Flow of list of favorite locations
     */
    @Query("SELECT * FROM favorite_locations ORDER BY cityName ASC")
    fun getAllFavorites(): Flow<List<FavoriteLocation>>
    
    /**
     * Retrieves all favorite locations as a list (for one-time reads).
     * 
     * @return List of favorite locations
     */
    @Query("SELECT * FROM favorite_locations ORDER BY cityName ASC")
    suspend fun getAllFavoritesList(): List<FavoriteLocation>
    
    /**
     * Retrieves a specific favorite location by city name.
     * 
     * @param cityName The name of the city to search for
     * @return FavoriteLocation if found, null otherwise
     */
    @Query("SELECT * FROM favorite_locations WHERE cityName = :cityName LIMIT 1")
    suspend fun getFavoriteByCityName(cityName: String): FavoriteLocation?
    
    /**
     * Retrieves a specific favorite location by ID.
     * 
     * @param id The ID of the favorite location
     * @return FavoriteLocation if found, null otherwise
     */
    @Query("SELECT * FROM favorite_locations WHERE id = :id LIMIT 1")
    suspend fun getFavoriteById(id: Long): FavoriteLocation?
    
    /**
     * Inserts a new favorite location.
     * 
     * @param favoriteLocation The favorite location to insert
     * @return The ID of the inserted location
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteLocation: FavoriteLocation): Long
    
    /**
     * Updates an existing favorite location.
     * 
     * @param favoriteLocation The favorite location to update
     */
    @Update
    suspend fun updateFavorite(favoriteLocation: FavoriteLocation)
    
    /**
     * Deletes a favorite location.
     * 
     * @param favoriteLocation The favorite location to delete
     */
    @Delete
    suspend fun deleteFavorite(favoriteLocation: FavoriteLocation)
    
    /**
     * Deletes a favorite location by city name.
     * 
     * @param cityName The name of the city to delete
     */
    @Query("DELETE FROM favorite_locations WHERE cityName = :cityName")
    suspend fun deleteFavoriteByCityName(cityName: String)
    
    /**
     * Deletes all favorite locations.
     */
    @Query("DELETE FROM favorite_locations")
    suspend fun deleteAllFavorites()
}

