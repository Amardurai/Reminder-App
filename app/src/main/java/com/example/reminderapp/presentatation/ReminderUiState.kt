package com.example.reminderapp.presentatation

import com.example.reminderapp.domain.model.MeetingReminder

data class ReminderUiState(
    val reminders: List<MeetingReminder> = emptyList(),
)
