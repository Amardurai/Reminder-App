package com.example.reminderapp.domain.repository

import com.example.reminderapp.domain.model.MeetingReminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

    suspend fun insertReminder(meetingReminder: MeetingReminder)
    suspend fun deleteReminder(meetingReminder: MeetingReminder)
    suspend fun updateReminder(meetingReminder: MeetingReminder)
    fun getReminders(): Flow<List<MeetingReminder>>

}