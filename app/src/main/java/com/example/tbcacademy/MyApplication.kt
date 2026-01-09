package com.example.tbcacademy

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import com.example.tbcacademy.presentation.notifications.NotificationChannels
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        com.google.firebase.FirebaseApp.initializeApp(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val generalChannel = NotificationChannel(
                NotificationChannels.GENERAL,
                "General Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val deeplinkChannel = NotificationChannel(
                NotificationChannels.DEEPLINK,
                "Deeplink Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(generalChannel)
            manager.createNotificationChannel(deeplinkChannel)
        }

        fetchFcmToken()
    }

    private fun fetchFcmToken() {
        com.google.firebase.messaging.FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.d("FCM", "Manual token fetch: $token")
                } else {
                    Log.e("FCM", "Token fetch failed", task.exception)
                }
            }
    }
}
