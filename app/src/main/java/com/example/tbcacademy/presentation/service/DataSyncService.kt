package com.example.tbcacademy.presentation.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.tbcacademy.MainActivity
import com.example.tbcacademy.R
import com.example.tbcacademy.common.SyncEventBus
import com.example.tbcacademy.common.SyncResult
import com.example.tbcacademy.domain.usecase.GetPostsUseCase
import com.example.tbcacademy.domain.usecase.GetStoriesUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DataSyncService : Service() {

    @Inject
    lateinit var getStoriesUseCase: GetStoriesUseCase

    @Inject
    lateinit var getPostsUseCase: GetPostsUseCase

    @Inject
    lateinit var syncEventBus: SyncEventBus

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    companion object {
        private const val CHANNEL_ID = "data_sync_channel"
        private const val NOTIFICATION_ID = 1

        fun start(context: Context) {
            val intent = Intent(context, DataSyncService::class.java)
            context.startForegroundService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, DataSyncService::class.java)
            context.stopService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotification(
            title = getString(R.string.syncing_data_),
            text = getString(R.string.fetching_latest_stories_and_posts),
            showProgress = true
        )
        startForeground(NOTIFICATION_ID, notification)

        serviceScope.launch {
            try {
                updateNotification(getString(R.string.fetching_stories), true)
                val storiesResult = getStoriesUseCase()
                val storiesCount = (storiesResult as? com.example.tbcacademy.common.Resource.Success)?.data?.size ?: 0

                delay(500)

                updateNotification(getString(R.string.fetching_posts), true)
                val postsResult = getPostsUseCase()
                val postsCount = (postsResult as? com.example.tbcacademy.common.Resource.Success)?.data?.size ?: 0

                val success = storiesResult is com.example.tbcacademy.common.Resource.Success &&
                        postsResult is com.example.tbcacademy.common.Resource.Success

                syncEventBus.emitSyncResult(
                    SyncResult(
                        success = success,
                        storiesCount = storiesCount,
                        postsCount = postsCount,
                        errorMessage = if (!success) {
                            (storiesResult as? com.example.tbcacademy.common.Resource.Error)?.errorMessage
                                ?: (postsResult as? com.example.tbcacademy.common.Resource.Error)?.errorMessage
                                ?: getString(R.string.unknown_error)
                        } else null
                    )
                )

                updateNotification(
                    if (success) getString(
                        R.string.sync_complete_stories_posts_,
                        storiesCount,
                        postsCount
                    )
                    else getString(R.string.sync__failed__),
                    showProgress = false
                )

                delay(2000)
                stopSelf()
            } catch (e: Exception) {
                syncEventBus.emitSyncResult(
                    SyncResult(success = false, errorMessage = e.message)
                )
                updateNotification(getString(R.string.sync_failed__, e.message), showProgress = false)
                delay(2000)
                stopSelf()
            }
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
    override fun onDestroy() { super.onDestroy(); serviceScope.cancel() }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID, getString(R.string.data_sync), NotificationManager.IMPORTANCE_LOW
        ).apply { description = getString(R.string.background_data_synchronization_) }
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    private fun createNotification(title: String, text: String, showProgress: Boolean = false): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(showProgress)
            .apply { if (showProgress) setProgress(0, 0, true) }
            .build()
    }

    private fun updateNotification(text: String, showProgress: Boolean = false) {
        val notification = createNotification(getString(R.string.tbc_academy), text, showProgress)
        getSystemService(NotificationManager::class.java).notify(NOTIFICATION_ID, notification)
    }
}
