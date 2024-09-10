package com.example.reminderapp.presentatation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reminderapp.R


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipAndTextFieldLayout(
    modifier: Modifier = Modifier,
    chipDataList: List<String> = emptyList(),
    onChipAdded: (String) -> Unit,
    chip: @Composable (data: String, index: Int) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var guestDropDown by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(rememberNestedScrollInteropConnection())
            .windowInsetsPadding(WindowInsets.ime)
    ) {
        Text(
            text = "Invite Guest",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(12.dp))

        FlowRow(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            chipDataList.forEachIndexed { index, chipData ->
                chip(chipData, index)
            }

            Box(
                modifier = Modifier
                    .weight(1f).height(40.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            guestDropDown = it.isFocused && text.isNotEmpty()
                        },
                    value = text,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    onValueChange = {
                        text = it
                        guestDropDown = it.isNotEmpty()
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (text.isNotEmpty()) {
                                onChipAdded(text)
                                text = ""
                            }
                        }
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 20.sp),
                )
            }
        }
    }
}


@Composable
fun GuestChip(
    guest: String,
    onDismiss: () -> Unit = {},
) {
    InputChip(
        modifier = Modifier,
        shape = RoundedCornerShape(50),
        enabled = true,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = .7f)),
        colors = InputChipDefaults.inputChipColors().copy(
            selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = .2f),
            disabledLabelColor = Color.White
        ),
        onClick = {
            onDismiss()
        },
        label = { Text(guest) },
        selected = true,
        avatar = {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Localized description",
                Modifier.size(InputChipDefaults.AvatarSize)
            )
        },
        trailingIcon = {
            Icon(
                Icons.Filled.Close,
                contentDescription = "Localized description",
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = .4f))
                    .size(16.dp)
                    .padding(2.dp),
                tint = Color(0xFFE0E0E0)
            )
        },
    )
}