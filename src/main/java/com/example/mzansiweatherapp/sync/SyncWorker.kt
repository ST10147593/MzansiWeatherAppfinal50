package com.example.mzansiweatherapp.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.mzansiweatherapp.data.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SyncWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    private val repo = WeatherRepository(appContext)

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val res = repo.refreshFromRemote()
            if (res.isSuccess) Result.success() else Result.retry()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
