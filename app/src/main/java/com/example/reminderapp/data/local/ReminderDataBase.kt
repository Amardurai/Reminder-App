package com.example.reminderapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reminderapp.domain.model.Reminder

@Database(entities = [Reminder::class], version = 1, exportSchema = false)
abstract class ReminderDataBase : RoomDatabase() {
    abstract val reminderDao: ReminderDao
}