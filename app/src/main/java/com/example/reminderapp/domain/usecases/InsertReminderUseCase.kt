package com.example.reminderapp.domain.usecases

import com.example.reminderapp.domain.model.MeetingReminder
import com.example.reminderapp.domain.repository.ReminderRepository
import javax.inject.Inject

class InsertReminderUseCase @Inject constructor(val reminderRepository: ReminderRepository) {

    suspend operator fun invoke(reminder: MeetingReminder) =
        reminderRepository.insertReminder(reminder)

}