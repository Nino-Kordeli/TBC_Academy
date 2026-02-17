package com.example.tbcacademy

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.data.worker.DailyResetWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        scheduleDailyReset()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    private fun scheduleDailyReset() {

        val initialDelay = calculateInitialDelayToMidnight()

        val request = PeriodicWorkRequestBuilder<DailyResetWorker>(
            1, TimeUnit.DAYS
        ).setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .setConstraints(Constraints.Builder().build())
            .build(
            )

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_reset_work",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    private fun calculateInitialDelayToMidnight() : Long {
        val now = Calendar.getInstance()

        val nextMidnight = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        return nextMidnight.timeInMillis - now.timeInMillis

    }
}