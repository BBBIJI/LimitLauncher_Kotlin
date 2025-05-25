package com.example.myapplication.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.Switch
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.myapplication.AppViewModel
import com.example.myapplication.CustomActionButton
import com.example.myapplication.Device
import com.example.myapplication.R
import com.example.myapplication.navigation.Screens
import kotlin.math.roundToInt
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ChildSettingsMenu(
    modifier: Modifier,
    navController: NavHostController,
    viewmodel: AppViewModel,
    paddingValues: PaddingValues,
    args: Screens.ChildSettingsMenu
) {
    val devices = viewmodel.loginResponse?.data?.devices?.filter { it.childId == args.childId }
    val allDevicesActive = devices?.all { device ->
        viewmodel.getMonitorState(device.childId)
    } == true

    val userId = viewmodel.loginResponse?.data?.userId
    val context = LocalContext.current
    val deleteResult by viewmodel.deleteResult.collectAsState()

    val currentUnixTimestamp: Long = Instant.now().epochSecond
    val dateTime = Instant.ofEpochSecond(currentUnixTimestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val formattedTime = dateTime.format(formatter)

    LaunchedEffect(deleteResult) {
        deleteResult?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewmodel.clearDeleteResult()
        }
    }

    val iconPainter = when (args.childIcon) {
        "0" -> painterResource(R.drawable.blue_round_01)
        "1" -> painterResource(R.drawable.pink_round_01)
        "2" -> painterResource(R.drawable.orange_round_01)
        "3" -> painterResource(R.drawable.pink_round_01)
        "4" -> painterResource(R.drawable.purple_round_01)
        "5" -> painterResource(R.drawable.yellow_round_01)
        else -> painterResource(R.drawable.blue_round_01)
    }

    val onColor = when (args.childIcon) {
        "0" -> Color(0xFF41B5FF)
        "1" -> Color(0xFFFF89CA)
        "2" -> Color(0xFFFF7D3B)
        "3" -> Color(0xFFFF89CA)
        "4" -> Color(0xFFA667FF)
        "5" -> Color(0xFFFFD048)
        else -> Color(0xFF41B5FF)
    }

    Column(modifier = Modifier.padding(paddingValues)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            shape = RoundedCornerShape(topEnd = 50.dp, topStart = 50.dp, bottomEnd = 16.dp, bottomStart = 16.dp),
        ) {
            CustomActionButton(
                modifier = Modifier,
                navController,
                null,
                viewmodel,
                args.childName,
                iconPainter,
                onColor,
                childId = args.childId,
                formattedTime,
                allDevicesActive
            )

            val lazyListState = rememberLazyListState()
            LazyColumn(state = lazyListState) {
                if (devices != null) {
                    items(devices) { device ->
                        DeviceButton(
                            modifier, device, viewmodel, userId
                        )
                    }
                } else {
                    item {
                        Text(text = "No devices found")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.navigate(Screens.ChildSettings(args.childId)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp, vertical = 16.dp)
        ) {
            Text(text = "CHILD SETTINGS")
        }
    }

    if (viewmodel.isonLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)) // Dim background
                .zIndex(1f) // Ensure it's above all
                .clickable(enabled = true, onClick = {}) // Block touches
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(32.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun DeviceButton(
    modifier: Modifier,
    device: Device,
    viewmodel: AppViewModel,
    userId: Long?
) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val anchors = mapOf(0f to 0, -200f to 1)

    // Observe monitorActive state for all devices
    val monitorActive by viewmodel.monitorStates.collectAsState()

    if (viewmodel.deleteDevicePopupState) {
        DeleteDeviceDialog(
            onDismissRequest = { viewmodel.deleteDevicePopupState = false },
            userId = userId,
            uuid = device.deviceUuid,
            viewModel = viewmodel
        )
    }

    // Get the monitorActive value for the specific device using its childId
    val isMonitorActive = monitorActive[device.childId] ?: false

    Column(modifier = Modifier.background(Color.Transparent)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(Color.Transparent)
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    orientation = Orientation.Horizontal,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) }
                )
        ) {
            // Background (Edit/Delete)
            Row(
                modifier = Modifier.fillMaxSize().background(Color.Transparent),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .padding(horizontal = 36.dp)
                ) {
                    Text("Edit", modifier = Modifier.clickable {}, color = Color.Black)
                    HorizontalDivider(modifier = Modifier.width(50.dp))
                    Text("Delete", modifier = Modifier.clickable {
                        viewmodel.deleteDevicePopupState = true
                    }, color = Color.Red)
                }
            }

            // Foreground
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                    .clip(RoundedCornerShape(50.dp))
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        contentColor = Color.Black,
                        containerColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.computer),
                            contentDescription = null,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        Text(text = device.deviceName, modifier = Modifier.padding(start = 16.dp))
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(
                            checked = isMonitorActive,  // Use the observed state here
                            onCheckedChange = {
                                viewmodel.toggleMonitor(it, device.childId)  // Update the ViewModel when toggled
                            },
                            modifier = Modifier
                                .padding(end = 24.dp)
                                .scale(1.5f)
                        )
                    }
                }
            }
        }
    }
}




@Composable
fun DeleteDeviceDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    userId : Long?,
    uuid : String,
    viewModel: AppViewModel
    ) {

    val context = LocalContext.current

    AlertDialog(
        title = {
            Text(text = "Delete?")
        },
        text = {
            Text(text = "delete")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = { viewModel.deleteDevice(userId,uuid, context)}
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )

}