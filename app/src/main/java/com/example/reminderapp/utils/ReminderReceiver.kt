package com.example.reminderapp.utils

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.example.reminderapp.CHANNEL_ID
import com.example.reminderapp.R
import com.example.reminderapp.domain.model.MeetingReminder
import com.google.gson.Gson

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val reminderJson = intent?.getStringExtra(AlarmUtils.REMINDER)
        val reminder = Gson().fromJson(reminderJson, MeetingReminder::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val notification = Notification.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(reminder.title)
                    .setContentText(reminder.description)
            }
        }
    }
}