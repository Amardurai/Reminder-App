package com.example.reminderapp.presentatation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun ReminderListScreen(
    uiState: ReminderUiState
) {
    val isTimePickerVisible = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = { ReminderListTopAppBar() }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderListTopAppBar() {
    TopAppBar(
        title = { Text(text = "Reminders") },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    )
}

@Composable
private fun PreviewReminderListScreen() {
    ReminderListScreen()
}