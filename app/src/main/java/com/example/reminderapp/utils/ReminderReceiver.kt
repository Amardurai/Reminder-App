package com.example.reminderapp.utils

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.reminderapp.CHANNEL_ID
import com.example.reminderapp.R
import com.example.reminderapp.domain.model.MeetingReminder
import com.example.reminderapp.domain.usecases.UpdateReminderUseCase
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {

    @Inject
    lateinit var updateReminderUseCase: UpdateReminderUseCase

    private lateinit var mediaPlayer:MediaPlayer

    override fun onReceive(context: Context, intent: Intent) {
        val reminderJson = intent.getStringExtra(AlarmUtils.REMINDER)
        val reminder = Gson().fromJson(reminderJson, MeetingReminder::class.java)
        mediaPlayer = MediaPlayer.create(context, R.raw.alarm_music)
        val donePendingIntent = createPendingIntent(
            context,
            reminderJson,
            Constants.DONE,
            reminder.timeInMillis.toInt()
        )
        val cancelPendingIntent = createPendingIntent(
            context,
            reminderJson,
            Constants.REJECT,
            reminder.timeInMillis.toInt()
        )


        when (intent.action) {
            Constants.DONE -> {
                mediaPlayer.stop()
                runBlocking {
                    updateReminderUseCase.invoke(reminder.copy(isCompleted = true))
                    AlarmUtils.cancelAlarm(context, reminder)
                }
            }

            Constants.REJECT -> {
                mediaPlayer.stop()
                runBlocking {
                    updateReminderUseCase.invoke(reminder.copy(isCompleted = true))
                    AlarmUtils.cancelAlarm(context, reminder)
                }
            }

            else -> {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }

                val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(reminder.title)
                    .setContentText(reminder.description)
                    .addAction(R.drawable.baseline_check_24, "Done", donePendingIntent)
                    .addAction(R.drawable.baseline_close_24, "Cancel", cancelPendingIntent)
                    .build()

                NotificationManagerCompat.from(context).notify(1, notification)
                mediaPlayer.release()
                mediaPlayer.start()
            }
        }

    }

    private fun createPendingIntent(
        context: Context,
        reminderJson: String?,
        action: String,
        requestCode: Int
    ): PendingIntent {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(AlarmUtils.REMINDER, reminderJson)
            this.action = action
        }
        return PendingIntent.getBroadcast(
            context, requestCode, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

}
