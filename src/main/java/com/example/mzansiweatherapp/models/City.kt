package com.example.mzansiweatherapp.models

data class City(
    val name1: Long,
    val name: String,    // in Celsius
    val currentTempC1: String,
    val currentTempC: Double,
    val rainProbabilityPercent: Int
)
