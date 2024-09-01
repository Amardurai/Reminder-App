package com.example.reminderapp.presentatation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.di.UseCases
import com.example.reminderapp.domain.model.MeetingReminder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderMeetingViewModel @Inject constructor(val useCases: UseCases) : ViewModel() {

    val uiState = useCases.getAllReminderUseCase.invoke().map {
        ReminderUiState(it)
    }.stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(500), ReminderUiState())

    fun addReminder(meetingReminder: MeetingReminder) {
        viewModelScope.launch {
            useCases.insertReminderUseCase.invoke(meetingReminder)
        }
    }

    fun deleteReminder(meetingReminder: MeetingReminder) {
        viewModelScope.launch {
            useCases.deleteReminderUseCase.invoke(meetingReminder)
        }
    }

    fun updateReminder(meetingReminder: MeetingReminder) {
        viewModelScope.launch {
            useCases.updateReminderUseCase.invoke(meetingReminder)
        }
    }
}