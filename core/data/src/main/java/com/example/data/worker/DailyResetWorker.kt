package com.example.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.repository.UserPreferencesRepositoryImpl
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DailyResetWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val userPreferencesRepository: UserPreferencesRepositoryImpl
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            userPreferencesRepository.checkAndResetDailyData()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}