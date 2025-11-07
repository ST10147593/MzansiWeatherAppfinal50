package com.example.mzansiweatherapp.data

import android.content.Context
import com.example.mzansiweatherapp.data.room.AppDatabase
import com.example.mzansiweatherapp.data.room.CityEntity
import com.example.mzansiweatherapp.models.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val ERROR.rainfallProbability: Int
private val ERROR.temperature: Double
private val ERROR.province: String
private val ERROR.name: String
private val ERROR.id: Long

class WeatherRepository(private val context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val cityDao = db.cityDao()
    private val api = RetrofitClient.instance

    class RetrofitClient {

    }

    // fetch from remote and cache locally
    suspend fun refreshFromRemote(): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext try {
            val resp = api.getAllCities().execute()
            if (resp.isSuccessful) {
                val body = resp.body() ?: emptyList()
                val entities = body.map { c ->
                    CityEntity(
                        id = c.id,
                        name = c.name,
                        province = c.province,
                        temperature = c.temperature,
                        rainfallProbability = c.rainfallProbability,
                        lastUpdated = System.currentTimeMillis()
                    )
                }
                cityDao.insertAll(entities)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Remote fetch failed: ${resp.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCityByName(name: String): City? = withContext(Dispatchers.IO) {
        val entity = cityDao.findByName(name)
        entity?.let { City(it.id, it.name, it.province, it.temperature, it.rainfallProbability) }
    }

    // Expose flow or short helper to get cached list
    fun observeCitiesFlow() = cityDao.getAllFlow()

    companion object
}
