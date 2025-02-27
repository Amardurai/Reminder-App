package com.example.reminderapp.domain.usecases

import com.example.reminderapp.domain.model.MeetingReminder
import com.example.reminderapp.domain.repository.ReminderRepository
import javax.inject.Inject

class UpdateReminderUseCase @Inject constructor(val reminderRepository: ReminderRepository) {

    suspend operator fun invoke(reminder: MeetingReminder) =
        reminderRepository.updateReminder(reminder)

}