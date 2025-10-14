package com.example.mzansiweatherapp.data

import com.example.mzansiweatherapp.models.City
import com.example.mzansiweatherapp.models.Province

object WeatherRepository {

    // Sample realistic data for demonstration
    private val westernCapeCities = listOf(
        City("Cape Town", 18.0, 15),
        City("Paarl", 20.5, 10),
        City("George", 17.5, 25)
    )

    private val gautengCities = listOf(
        City("Johannesburg", 22.0, 5),
        City("Pretoria", 23.5, 8),
        City("Soweto", 21.0, 12)
    )

    private val kwaZuluNatalCities = listOf(
        City("Durban", 26.0, 35),
        City("Pietermaritzburg", 22.0, 40),
        City("Richards Bay", 25.5, 30)
    )

    private val easternCapeCities = listOf(
        City("Port Elizabeth", 19.0, 20),
        City("East London", 18.5, 22)
    )

    private val limpopoCities = listOf(
        City("Polokwane", 25.0, 5),
        City("Thohoyandou", 24.0, 10)
    )

    private val northernCapeCities = listOf(
        City("Kimberley", 28.0, 2)
    )

    private val freeStateCities = listOf(
        City("Bloemfontein", 21.0, 6)
    )

    private val mpumalangaCities = listOf(
        City("Nelspruit", 24.0, 18)
    )

    private val northWestCities = listOf(
        City("Mahikeng", 23.0, 7)
    )

    private val provinces = listOf(
        Province("Western Cape", westernCapeCities),
        Province("Gauteng", gautengCities),
        Province("KwaZulu-Natal", kwaZuluNatalCities),
        Province("Eastern Cape", easternCapeCities),
        Province("Limpopo", limpopoCities),
        Province("Northern Cape", northernCapeCities),
        Province("Free State", freeStateCities),
        Province("Mpumalanga", mpumalangaCities),
        Province("North West", northWestCities)
    )

    fun getProvinces(): List<Province> = provinces

    fun findCityByName(query: String): City? {
        val q = query.trim().lowercase()
        return provinces.flatMap { it.cities }.find { it.name.lowercase() == q }
    }

    // fuzzy search across cities
    fun searchCities(term: String): List<Pair<Province, City>> {
        val q = term.trim().lowercase()
        val list = mutableListOf<Pair<Province, City>>()
        if (q.isEmpty()) return list
        provinces.forEach { p ->
            p.cities.forEach { c ->
                if (c.name.lowercase().contains(q) || p.name.lowercase().contains(q)) {
                    list.add(Pair(p, c))
                }
            }
        }
        return list
    }

    fun getAllCities(): List<Pair<Province, City>> {
        return provinces.flatMap { p -> p.cities.map { c -> Pair(p, c) } }
    }
}
