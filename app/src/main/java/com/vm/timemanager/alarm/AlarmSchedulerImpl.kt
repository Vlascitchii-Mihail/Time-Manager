package com.vm.timemanager.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.vm.timemanager.data.Task
import java.time.ZoneId

class AlarmSchedulerImpl(private val context: Context?, inputTask: Task) {

    private val alarmManger = context?.getSystemService(AlarmManager::class.java)
    private val taskDate = inputTask.startTime?.atZone(ZoneId.systemDefault())?.toInstant()!!.toEpochMilli()

    private val intent = Intent(context, AlarmReceiver::class.java)
        .putExtra(TimeNotificationService.TASK_NAME, inputTask.taskName)
        .putExtra(TimeNotificationService.TASK_DESCRIPTION, inputTask.taskDescription)
        .putExtra(TimeNotificationService.TASK_DATE, taskDate)
        .putExtra(TimeNotificationService.TASK_DAY, inputTask.day)
        .putExtra(TimeNotificationService.TASK_ID, inputTask.id)

    private var pendingIntent = getPendingIntent(inputTask)

    fun setAlarm() {
        alarmManger?.setExact(
            AlarmManager.RTC_WAKEUP,
            taskDate,
            pendingIntent
        )
    }

    fun cancelAlarm(task: Task) {
        alarmManger?.cancel(getPendingIntent(task))
    }

    private fun getPendingIntent(inputTask: Task): PendingIntent = PendingIntent.getBroadcast(
        context,
        //unique per alarm
        //todo: request code = taskId
        inputTask.id,
        intent,
        PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE
    )
}
