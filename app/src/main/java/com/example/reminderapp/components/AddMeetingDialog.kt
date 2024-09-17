package com.example.reminderapp.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.reminderapp.domain.model.MeetingReminder
import com.example.reminderapp.presentatation.Actions
import com.example.reminderapp.presentatation.BottomSheetContent
import com.example.reminderapp.utils.AlarmUtils
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMeetingDialog(
    showAddMeetingDialog: MutableState<Boolean>,
    modelSheetState: SheetState,
    timeInMilli: Long,
    dateFormat: SimpleDateFormat,
    onTimeClick: () -> Unit,
    onAction: (Actions) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    if (showAddMeetingDialog.value) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    modelSheetState.hide()
                }.invokeOnCompletion {
                    showAddMeetingDialog.value = false
                }
            },
            sheetState = modelSheetState,
            windowInsets = WindowInsets.ime
        ) {
            BottomSheetContent(
                time = dateFormat.format(timeInMilli),
                onTimeClick = { onTimeClick() },
                onScheduleClick = { title, description, isRepeat ->
                    val meeting = MeetingReminder(
                        title, timeInMilli, description, false, isRepeat
                    )
                    onAction(Actions.AddMeeting(meeting))
                    if (isRepeat) AlarmUtils.repeatAlarm(context, meeting)
                    else AlarmUtils.setUpAlarm(context, meeting)
                    showAddMeetingDialog.value = false
                }
            )
        }
    }
}
