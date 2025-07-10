package com.example.myapplication.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import com.example.myapplication.ToggleRequest
import java.util.Calendar

@Composable
fun ScreenTimeSettings(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavHostController,
) {

    var breakAfterTime by remember { mutableStateOf("00:00") }
    var breakTimeDuration by remember { mutableStateOf("00:00") }
    var totalAllowance by remember { mutableStateOf("00:00") }
    var showTimePicker by remember { mutableStateOf(false) }
    var showTimePicker2 by remember { mutableStateOf(false) }
    var showTimePicker3 by remember { mutableStateOf(false) }


    if (showTimePicker) {
        Dialog(onDismissRequest = { showTimePicker = false }) {
            InputExample(
                onDismiss = { showTimePicker = false },
                onConfirm = { selectedTime ->
                    breakAfterTime = selectedTime
                    showTimePicker = false
                }
            )
        }
    }

    if (showTimePicker2) {
        Dialog(onDismissRequest = { showTimePicker2 = false }) {
            InputExample(
                onDismiss = { showTimePicker2 = false },
                onConfirm = { selectedTime ->
                    breakTimeDuration = selectedTime
                    showTimePicker2 = false
                }
            )
        }
    }

    if (showTimePicker3) {
        Dialog(onDismissRequest = { showTimePicker3 = false }) {
            InputExample(
                onDismiss = { showTimePicker3 = false },
                onConfirm = { selectedTime ->
                    totalAllowance = selectedTime
                    showTimePicker3 = false
                }
            )
        }
    }



    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            shape = RoundedCornerShape(
                topEnd = 50.dp,
                topStart = 50.dp,
                bottomEnd = 16.dp,
                bottomStart = 16.dp
            ),
        ) {
            Box(modifier = Modifier) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF0f1a38),
                    ),
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Absolute.Center
                    ) {
                        Text(
                            "SCREEN TIME SETTINGS",
                            modifier = Modifier.padding(start = 16.dp),
                            color = Color.White
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                var mandatoryScreenTime by remember { mutableStateOf(true) }
                Text(text = "Mandatory Screen Time", modifier = Modifier.padding(horizontal = 30.dp))
                Switch(
                    checked = mandatoryScreenTime,
                    onCheckedChange = {
                        mandatoryScreenTime = it
                    },
                    modifier = modifier,
                    thumbContent = {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                )
            }
            Row(modifier = Modifier.padding(start = 48.dp, end = 16.dp)) {
                Text("Break after how long")
                Spacer(modifier.weight(0.1f))
                Text(
                    modifier = modifier.clickable { showTimePicker = true },
                    text = breakAfterTime
                )
            }
            Row(modifier = Modifier.padding(start = 48.dp, end = 16.dp)) {
                Text("Break time duration")
                Spacer(modifier.weight(0.1f))
                Text(
                    modifier = modifier.clickable { showTimePicker2 = true },
                    text = breakTimeDuration
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                var cumulativeScreenTime by remember { mutableStateOf(true) }
                Text(text = "Cumulative Screentime", modifier = Modifier.padding(horizontal = 30.dp))
                Switch(
                    checked = cumulativeScreenTime,
                    onCheckedChange = {
                        cumulativeScreenTime = it
                    },
                    modifier = modifier,
                    thumbContent = {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                )
            }
            Row(modifier = Modifier.padding(start = 48.dp, end = 16.dp)) {
                Text("Total allowance")
                Spacer(modifier.weight(0.1f))
                Text(
                    modifier = modifier.clickable { showTimePicker3 = true },
                    text = totalAllowance
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                var noScreenPeriod by remember { mutableStateOf(true) }
                Text(text = "No Screen Period", modifier = Modifier.padding(horizontal = 30.dp))
                Switch(
                    checked = noScreenPeriod,
                    onCheckedChange = {
                        noScreenPeriod = it
                    },
                    modifier = modifier,
                    thumbContent = {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                )
            }
            Row(modifier = Modifier.padding(start = 48.dp, end = 16.dp)) {
                Text("School hours")
                Spacer(modifier.weight(0.1f))
                Text("M - F")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        Spacer(modifier = Modifier.weight(0.1f))
        Button(
            onClick = {},
            modifier.fillMaxWidth().padding(36.dp)
        ) {
            Text("Save")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputExample(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimeInput(state = timePickerState)

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onDismiss) {
                    Text("Dismiss")
                }
                Button(onClick = {
                    val hour = timePickerState.hour
                    val minute = timePickerState.minute
                    val formatted = String.format("%02d:%02d", hour, minute)
                    onConfirm(formatted)
                }) {
                    Text("Confirm")
                }
            }
        }
    }
}

