package com.example.reminderapp.presentatation

import com.example.reminderapp.domain.model.MeetingReminder

sealed interface Actions {
    data class AddMeeting(val reminder: MeetingReminder) : Actions
}