package com.example.mzansiweatherapp.models

data class City(
    val name: String,
    val currentTempC: Double,    // in Celsius
    val rainProbabilityPercent: Int
)
