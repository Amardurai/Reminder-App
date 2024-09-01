package com.example.reminderapp.domain.usecases

import com.example.reminderapp.domain.model.MeetingReminder
import com.example.reminderapp.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllReminderUseCase @Inject constructor(val reminderRepository: ReminderRepository) {
    operator fun invoke() = reminderRepository.getReminders()
}