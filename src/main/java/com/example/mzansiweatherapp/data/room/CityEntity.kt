package com.example.mzansiweatherapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class CityEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val province: String,
    val temperature: Double,
    val rainfallProbability: Int,
    val lastUpdated: Long = System.currentTimeMillis()
)
