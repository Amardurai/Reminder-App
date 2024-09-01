package com.example.reminderapp.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.reminderapp.domain.model.MeetingReminder
import com.google.gson.Gson

object AlarmUtils {
    const val REMINDER = "reminder"
    fun setUpAlarm(context: Context, reminder: MeetingReminder) {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(REMINDER, Gson().toJson(reminder))
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.timeInMillis.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            reminder.timeInMillis,
            pendingIntent
        )

    }

    fun cancelAlarm(context: Context, reminder: MeetingReminder) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(REMINDER, Gson().toJson(reminder))
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.timeInMillis.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.cancel(pendingIntent)
    }

    fun repeatAlarm(context: Context, reminder: MeetingReminder) {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(REMINDER, Gson().toJson(reminder))
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.timeInMillis.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val interval = 2L * 60L * 1000L
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            reminder.timeInMillis,
            interval,
            pendingIntent
        )
    }
}