package com.example.tbcacademy.presentation.notifications

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessaging : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notificationHelper = NotificationHelper(this)

        when (message.data["type"]) {
            "deeplink_profile" -> {
                notificationHelper.showDeeplinkNotification()
            }

            "general" -> {
                notificationHelper.showGeneralNotification()
            }
        }
    }
}