package com.example.mzansiweatherapp.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Query("SELECT * FROM city ORDER BY name")
    fun getAllFlow(): Flow<List<CityEntity>>

    @Query("SELECT * FROM city WHERE name = :name LIMIT 1")
    suspend fun findByName(name: String): CityEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<CityEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: CityEntity)

    @Query("DELETE FROM city")
    suspend fun clearAll()
}
