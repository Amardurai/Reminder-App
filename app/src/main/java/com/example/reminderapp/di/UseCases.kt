package com.example.reminderapp.di

import com.example.reminderapp.domain.usecases.DeleteReminderUseCase
import com.example.reminderapp.domain.usecases.GetAllReminderUseCase
import com.example.reminderapp.domain.usecases.InsertReminderUseCase
import com.example.reminderapp.domain.usecases.UpdateReminderUseCase

data class UseCases(
    val insertReminderUseCase: InsertReminderUseCase,
    val deleteReminderUseCase: DeleteReminderUseCase,
    val updateReminderUseCase: UpdateReminderUseCase,
    val getAllReminderUseCase: GetAllReminderUseCase
)
