package com.example.reminderapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MeetingReminder(
    val title: String,
    @PrimaryKey
    val timeInMillis: Long,
    val description: String,
    val isCompleted: Boolean,
    val isRepeating: Boolean,
)
