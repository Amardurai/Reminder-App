package com.example.reminderapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
            value = meetingTitle, onValueChange = { meetingTitle = it },
            placeholder = { Text(text = "Title", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = meetingDescription, onValueChange = { meetingDescription = it },
            placeholder = { Text(text = "Description", color = MaterialTheme.colorScheme.onSurface.copy(0.5f)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = time, onValueChange = {}, enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .background(shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.background)
                .clickable { onTimeClick() },
            trailingIcon = {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Switch(
            checked = isRepeat, onCheckedChange = {
                isRepeat = it
            }, colors = SwitchDefaults.colors().copy(
                checkedTrackColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onScheduleClick(meetingTitle, meetingDescription, isRepeat) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Schedule")
        }
    }
}
