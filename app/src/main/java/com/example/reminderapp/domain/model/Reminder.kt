package com.example.reminderapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reminder(
    val name: String,
    val dosage: String,
    @PrimaryKey(autoGenerate = true)
    val timeInMillis: Long,
    val isTaken: Boolean = false,
    val isRepeating: Boolean = false,
)
