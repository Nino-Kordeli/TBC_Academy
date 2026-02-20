package com.example.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.domain.repository.UserPreferencesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DailyResetWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val userPreferencesRepository: UserPreferencesRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        userPreferencesRepository.checkAndResetDailyData()
        return Result.success()
    }
}