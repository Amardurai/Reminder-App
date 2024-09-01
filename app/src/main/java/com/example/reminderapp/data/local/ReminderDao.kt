package com.example.reminderapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.reminderapp.domain.model.MeetingReminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(meetingReminder: MeetingReminder)

    @Delete
    suspend fun deleteReminder(meetingReminder: MeetingReminder)

    @Update
    suspend fun updateReminder(meetingReminder: MeetingReminder)

    @Query("SELECT * FROM MeetingReminder ORDER BY timeInMillis DESC")
    fun getReminders(): Flow<List<MeetingReminder>>

}