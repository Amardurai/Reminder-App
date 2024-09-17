package com.example.reminderapp.presentatation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reminderapp.components.AddMeetingDialog
import com.example.reminderapp.components.TimePickerDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
/*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderListScreen(
    uiState: ReminderUiState,
    onAction: (Actions) -> Unit
) {

    val currentTime = Calendar.getInstance()
    val context = LocalContext.current
    var isTimePickerVisible by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )

    val dateFormat = remember {
        SimpleDateFormat("hh:mm a", Locale.getDefault())
    }

    var timeInMilli by remember {
        mutableLongStateOf(0L)
    }

    val showAddMeetingDialog = remember { mutableStateOf(false) }
    val modelSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    if (showAddMeetingDialog.value) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
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
                onTimeClick = {
                    isTimePickerVisible = true
                },
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

    if (isTimePickerVisible) {
        Dialog(onDismissRequest = { isTimePickerVisible = false }) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(state = timePickerState)
                Row {
                    Button(onClick = { isTimePickerVisible = false }) {
                        Text(text = "Cancel")
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = {
                        val calender = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                            set(Calendar.MINUTE, timePickerState.minute)
                        }
                        timeInMilli = calender.timeInMillis
                        isTimePickerVisible = false
                    }) {
                        Text(text = "Confirm")
                    }
                }
            }

        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Reminders") })
        },
        floatingActionButton = {
            FloatingButton(
                onAddClick = {
                    showAddMeetingDialog.value = true
                }
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {

        }
    }
}
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderListScreen(
    uiState: ReminderUiState,
    onAction: (Actions) -> Unit
) {
    var isTimePickerVisible by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(is24Hour = true)
    val dateFormat = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    var timeInMilli by remember { mutableLongStateOf(0L) }
    val showAddMeetingDialog = remember { mutableStateOf(false) }
    val modelSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    AddMeetingDialog(
        showAddMeetingDialog = showAddMeetingDialog,
        modelSheetState = modelSheetState,
        timeInMilli = timeInMilli,
        dateFormat = dateFormat,
        onTimeClick = { isTimePickerVisible = true },
        onAction = onAction
    )

    TimePickerDialog(
        timePickerState = timePickerState,
        isTimePickerVisible = isTimePickerVisible,
        onConfirm = { hour, minute ->
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }
            timeInMilli = calendar.timeInMillis
            isTimePickerVisible = false
        },
        onCancel = {
            isTimePickerVisible = false
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Reminders") })
        },
        floatingActionButton = {
            FloatingButton(
                onAddClick = { showAddMeetingDialog.value = true }
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {

        }
    }
}


@Composable
fun BottomSheetContent(
    time: String,
    onTimeClick: () -> Unit,
    onScheduleClick: (String, String, Boolean) -> Unit
) {
    var meetingTitle by remember { mutableStateOf("") }
    var meetingDescription by remember { mutableStateOf("") }
    var isRepeat by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TextField(
            value = meetingTitle, onValueChange = {
                meetingTitle = it
            },
            placeholder = {
                Text(text = "Title", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true,
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = meetingDescription,
            onValueChange = {
                meetingDescription = it
            },
            placeholder = {
                Text(text = "Description", color = MaterialTheme.colorScheme.onSurface.copy(0.5f))
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true,
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = time, onValueChange = {}, enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.background
                )
                .clickable { onTimeClick() },
            trailingIcon = {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .imePadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Repeat", style = MaterialTheme.typography.bodyLarge)
            Switch(
                checked = isRepeat, onCheckedChange = {
                    isRepeat = it
                }, colors = SwitchDefaults.colors().copy(
                    checkedTrackColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        val chipDataSnapshotStateList = remember {
            mutableStateListOf<String>()
        }

        ChipAndTextFieldLayout(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding(),
            chipDataList = chipDataSnapshotStateList,
            onChipAdded = { chipDataSnapshotStateList.add(it) }
        ) { guest, index ->
            GuestChip(guest, onDismiss = {
                chipDataSnapshotStateList.removeAt(index)
            })
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onScheduleClick(meetingTitle, meetingDescription, isRepeat) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Schedule", style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(24.dp))

    }
}

@Composable
@Preview(showBackground = true)
fun PreviewBottomSheetContentNew() {
    BottomSheetContent("",
        onTimeClick = {},
        onScheduleClick = { _, _, _ -> }
    )
}


@Composable
fun FloatingButton(onAddClick: () -> Unit) {

    ExtendedFloatingActionButton(
        text = { Text(text = "Add") },
        icon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
        onClick = { onAddClick() })

}

@Composable
private fun PreviewReminderListScreen() {
    ReminderListScreen(
        ReminderUiState(), {}
    )
}