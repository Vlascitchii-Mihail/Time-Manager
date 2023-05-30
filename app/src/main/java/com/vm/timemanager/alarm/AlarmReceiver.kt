package com.vm.timemanager.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.vm.timemanager.data.Task
import java.time.Instant
import java.time.LocalDateTime
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getIntExtra(TimeNotificationService.TASK_ID, 0)
        val taskName = intent.getStringExtra(TimeNotificationService.TASK_NAME)
        val taskDescription =  intent.getStringExtra(TimeNotificationService.TASK_DESCRIPTION)
        val taskDate =  intent.getLongExtra(TimeNotificationService.TASK_DATE, 0L)
        val taskDay =  intent.getStringExtra(TimeNotificationService.TASK_DAY)

        val service = TimeNotificationService(context)
        service.showNotification(
            taskName,
            taskDescription
        )

        //set new exact alarm here to date + 7 days
        val newTaskDate = taskDate + TimeUnit.DAYS.toMillis(7)
        AlarmSchedulerImpl(
            context,
            Task(taskId,
                taskDay,
                taskName!!,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(newTaskDate),  TimeZone.getDefault().toZoneId()),
                taskDescription!!)
        ).setAlarm()
    }
}