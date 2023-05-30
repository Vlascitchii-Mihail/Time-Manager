package com.vm.timemanager.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.vm.timemanager.MainActivity
import com.vm.timemanager.R

class TimeNotificationService(private val context: Context) {

    private val notificationManager = context.getSystemService(
        Context.NOTIFICATION_SERVICE
    ) as NotificationManager

    fun showNotification(
        taskName: String?,
        taskDescription: String?
    ) {
        val taskIntent = Intent(context, MainActivity::class.java)
        val taskPendingIntent = PendingIntent.getActivity(
            context,
            1,
            taskIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANEL_ID)
            .setSmallIcon(R.drawable.time_management_svgrepo_com)
            .setContentTitle(taskName ?: "Title Not")
            .setContentText(taskDescription ?: "Content Not")
            .setContentIntent(taskPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        const val NOTIFICATION_CHANEL_ID = "task_reminder_channel"
        const val NOTIFICATION_ID = 1001
        const val TASK_NAME = "task_name"
        const val TASK_DESCRIPTION = "task_description"
        const val TASK_DATE = "task_date"
        const val TASK_DAY = "task_day"
        const val TASK_ID = "task_ID"

    }
}
