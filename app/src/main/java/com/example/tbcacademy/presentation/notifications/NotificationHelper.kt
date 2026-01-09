package com.example.tbcacademy.presentation.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.tbcacademy.R
import androidx.core.net.toUri

class NotificationHelper(private val context: Context) {

    private val manager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showGeneralNotification() {
        val notification = NotificationCompat.Builder(context, NotificationChannels.GENERAL)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Hello")
            .setContentText("General notification")
            .setAutoCancel(true)
            .build()

        manager.notify(1, notification)
    }

    fun showDeeplinkNotification() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            "tbcacademy://profile".toUri()
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, NotificationChannels.DEEPLINK)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Profile")
            .setContentText("Open your profile")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        manager.notify(2, notification)
    }
}