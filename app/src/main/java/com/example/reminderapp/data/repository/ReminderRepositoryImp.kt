package com.example.reminderapp.data.repository

import com.example.reminderapp.data.local.ReminderDao
import com.example.reminderapp.domain.model.MeetingReminder
import com.example.reminderapp.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderRepositoryImp @Inject constructor(val reminderDao: ReminderDao) : ReminderRepository {
    override suspend fun insertReminder(meetingReminder: MeetingReminder) {
        reminderDao.insertReminder(meetingReminder)
    }

    override suspend fun deleteReminder(meetingReminder: MeetingReminder) {
        reminderDao.deleteReminder(meetingReminder)
    }

    override suspend fun updateReminder(meetingReminder: MeetingReminder) {
        reminderDao.updateReminder(meetingReminder)
    }

    override fun getReminders(): Flow<List<MeetingReminder>> {
        return reminderDao.getReminders()
    }

}