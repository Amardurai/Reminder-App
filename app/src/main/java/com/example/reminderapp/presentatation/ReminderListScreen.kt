package com.example.reminderapp.presentatation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderListScreen(
    uiState: ReminderUiState
) {

    val isTimePickerVisible = remember {
        mutableStateOf(false)
    }
    val showAddMeetingDialog = remember { mutableStateOf(false) }
    val modelSheetState = rememberModalBottomSheetState()
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
            sheetState = modelSheetState
        ) {
            BottomSheetContent(
                time = "Pick Time",
                onTimeClick = {
                    isTimePickerVisible.value = true
                },
                onScheduleClick = {

                }
            )
        }
    }

    Scaffold(
        topBar = {
            ReminderListTopAppBar(
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

@Composable
fun BottomSheetContent(
    time: String = "12:44",
    onTimeClick: () -> Unit,
    onScheduleClick: () -> Unit
) {
    val meetingTitle = remember { mutableStateOf("") }
    val meetingDescription = remember { mutableStateOf("") }
    val isRepeat = remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        TextField(
            value = meetingTitle.value, onValueChange = {
                meetingTitle.value = it
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
            value = meetingDescription.value,
            onValueChange = {
                meetingDescription.value = it
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
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Repeat", style = MaterialTheme.typography.bodyLarge)
            Switch(
                checked = isRepeat.value, onCheckedChange = {
                    isRepeat.value = it
                }, colors = SwitchDefaults.colors().copy(
                    checkedTrackColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { onScheduleClick() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Schedule", style = MaterialTheme.typography.bodyLarge)
        }

    }
}

@Composable
@Preview(showBackground = true)
fun PreviewBottomSheetContentNew() {
    BottomSheetContent(
        onTimeClick = {},
        onScheduleClick = {}
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderListTopAppBar(onAddClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Reminders") },
        actions = {
            IconButton(onClick = { onAddClick() }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    )
}

@Composable
private fun PreviewReminderListScreen() {
    ReminderListScreen(
        ReminderUiState()
    )
}