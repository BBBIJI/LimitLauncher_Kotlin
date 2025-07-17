package com.example.myapplication.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.AppViewModel

@Composable
fun Devices(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: AppViewModel
) {
    DeviceListScreen(paddingValues = paddingValues, viewModel = viewModel)
}

@Composable
fun DeviceListScreen(
    paddingValues: PaddingValues,
    viewModel: AppViewModel
) {
    val context = LocalContext.current
    val childDevices = viewModel.loginResponse?.data?.devices ?: emptyList()
    val parentDevices = viewModel.loginResponse?.data?.parentDevices ?: emptyList()
    val isEditMode = viewModel.isEditMode

    val showPopup = viewModel.deleteDevicePopupState
    ConfirmDeletePopup(
        show = showPopup,
        onDismiss = { viewModel.deleteDevicePopupState = false },
        onConfirm = {
            val userId = viewModel.loginResponse?.data?.userId
            viewModel.selectedDeviceUuids.forEach { uuid ->
                viewModel.deleteDevice(userId, uuid, context)
            }
            viewModel.clearSelectedDevices()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(24.dp)
    ) {
        // Top Bar with Edit Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = if (isEditMode) "Done" else "Edit",
                color = Color(0xFF4059AD),
                fontSize = 16.sp,
                modifier = Modifier.clickable {
                    viewModel.toggleEditMode()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Parent Devices Section (view-only)
        Text(
            text = "Parent Devices",
            color = Color(0xFF4059AD),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(parentDevices) { parent ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = parent.deviceName ?: "Unnamed Device",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Child Devices Section (editable + deletable)
        Text(
            text = "Child Devices",
            color = Color(0xFF4059AD),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(childDevices) { child ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = child.deviceName ?: "Unnamed Device",
                        modifier = Modifier.weight(1f)
                    )

                    if (isEditMode) {
                        Checkbox(
                            checked = child.deviceUuid in viewModel.selectedDeviceUuids,
                            onCheckedChange = {
                                viewModel.toggleDeviceSelection(child.deviceUuid)
                            }
                        )
                    }
                }
            }
        }

        if (isEditMode && viewModel.selectedDeviceUuids.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Delete Selected",
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        viewModel.deleteDevicePopupState = true
                    }
            )
        }
    }
}

@Composable
fun ConfirmDeletePopup(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (show) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete the selected device(s)?") },
            confirmButton = {
                Text(
                    "Delete",
                    color = Color.Red,
                    modifier = Modifier.clickable { onConfirm() }
                )
            },
            dismissButton = {
                Text(
                    "Cancel",
                    modifier = Modifier.clickable { onDismiss() }
                )
            }
        )
    }
}

