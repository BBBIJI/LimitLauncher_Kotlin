package com.example.myapplication.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.AppViewModel
import com.example.myapplication.Child
import com.example.myapplication.navigation.Screens
import com.example.myapplication.timeStringToMinutes
import java.util.*

@Composable
fun ScreenTimeSettings(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavHostController,
    viewModel: AppViewModel,
    args: Screens.ScreenTimeSettings
) {
    var breakAfterTime by remember { mutableStateOf("00:00") }
    var breakTimeDuration by remember { mutableStateOf("00:00") }
    var totalAllowance by remember { mutableStateOf("00:00") }

    var activePickerField by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.refreshChildren(context)
    }

    if (activePickerField != null) {
        Dialog(onDismissRequest = { activePickerField = null }) {
            TimePickerDialog(
                onConfirm = { selectedTime ->
                    when (activePickerField) {
                        "breakAfter" -> breakAfterTime = selectedTime
                        "breakDuration" -> breakTimeDuration = selectedTime
                        "totalAllowance" -> totalAllowance = selectedTime
                    }
                    activePickerField = null
                },
                onDismiss = { activePickerField = null }
            )
        }
    }

    Column(modifier = Modifier.padding(paddingValues)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            shape = RoundedCornerShape(
                topEnd = 50.dp,
                topStart = 50.dp,
                bottomEnd = 16.dp,
                bottomStart = 16.dp
            )) {
            Column {
                // Header
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

                // Mandatory screen time
                SwitchRow(
                    label = "Mandatory Screen Time",
                    checked = viewModel.isMandatoryScreenTimeEnabled,
                    onCheckedChange = { viewModel.isMandatoryScreenTimeEnabled = it }
                )

                // Break After
                TimeRow(
                    label = "Break after how long",
                    value = breakAfterTime,
                    onClick = { activePickerField = "breakAfter" },
                    enabled = viewModel.isMandatoryScreenTimeEnabled
                )

                // Break Duration
                TimeRow(
                    label = "Break time duration",
                    value = breakTimeDuration,
                    onClick = { activePickerField = "breakDuration" },
                    enabled = viewModel.isMandatoryScreenTimeEnabled
                )

                // Cumulative screen time
                SwitchRow(
                    label = "Cumulative Screentime",
                    checked = viewModel.isCumulativeScreentimeEnabled,
                    onCheckedChange = { viewModel.isCumulativeScreentimeEnabled = it }
                )

                // Total Allowance
                TimeRow(
                    label = "Total allowance",
                    value = totalAllowance,
                    onClick = { activePickerField = "totalAllowance" },
                    enabled = viewModel.isCumulativeScreentimeEnabled
                )

                // No Screen Period
                SwitchRow(
                    label = "No Screen Period",
                    checked = viewModel.isNoScreenPeriodOn,
                    onCheckedChange = { viewModel.isNoScreenPeriodOn = it }
                )

                // Static row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("School hours")
                    Text("M - F")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Spacer(modifier = Modifier.weight(0.1f))
        Button(
            onClick = {
                val breakAfterMinutes = timeStringToMinutes(breakAfterTime)
                val breakDurationMinutes = timeStringToMinutes(breakTimeDuration)
                val totalAllowedMinutes = timeStringToMinutes(totalAllowance)

                viewModel.updateMainSwitchTime(
                    childId = args.childId,
                    onTimeMinutes = if (viewModel.isMandatoryScreenTimeEnabled) breakAfterMinutes else -1440,
                    offTimeMinutes = if (viewModel.isMandatoryScreenTimeEnabled) breakDurationMinutes else 0,
                    cumulativeTime = if (viewModel.isCumulativeScreentimeEnabled) totalAllowedMinutes else -1440,
                    context = context,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 36.dp, vertical = 12.dp)
        ) {
            Text("Save")
        }

    }
}


@Composable
fun TimeRow(label: String, value: String, onClick: () -> Unit, enabled: Boolean = true) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        Text(
            text = value,
            modifier = Modifier
                .clickable(enabled = enabled) { onClick() },
            color = if (enabled) MaterialTheme.colorScheme.primary else Color.Gray
        )
    }
}

@Composable
fun SwitchRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            thumbContent = {
                if (checked) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize)
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val currentTime = Calendar.getInstance()
    val state = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
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
            TimeInput(state = state)

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = onDismiss) {
                    Text("Dismiss")
                }
                Button(onClick = {
                    val time = String.format("%02d:%02d", state.hour, state.minute)
                    onConfirm(time)
                }) {
                    Text("Confirm")
                }
            }
        }
    }
}
