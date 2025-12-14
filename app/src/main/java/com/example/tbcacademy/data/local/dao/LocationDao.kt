package com.example.tbcacademy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tbcacademy.data.local.entity.LocationEntity

@Dao
interface LocationDao {

    @Query("SELECT * FROM locations")
    suspend fun getAllLocations(): List<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<LocationEntity>)

    @Query("DELETE FROM locations")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM locations")
    suspend fun getCount(): Int
}