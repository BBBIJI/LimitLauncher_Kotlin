package com.example.myapplication.pages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.myapplication.AppViewModel
import com.example.myapplication.CustomActionButton
import com.example.myapplication.R
import com.example.myapplication.navigation.Screens
import kotlinx.coroutines.delay
import kotlin.math.roundToInt


@Composable
fun MainPage(viewModel: AppViewModel, navController: NavHostController, paddingValues: PaddingValues) {
    val children = viewModel.loginResponse?.data?.children ?: emptyList()
    val userId = viewModel.loginResponse?.data?.userId ?: 0
    val context = LocalContext.current
    val deleteResult by viewModel.deleteResult.collectAsState()


    LaunchedEffect(deleteResult) {
        deleteResult?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearDeleteResult()
        }
    }

    // Observe monitorStates to trigger UI updates
    val monitorStates by viewModel.monitorStates.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        item {
            Box(
                modifier = Modifier.padding(start = 36.dp, end = 36.dp, top = 36.dp, bottom = 16.dp)
            ) {
                Text("Hello ${viewModel.loginResponse?.data?.firstName}")
            }
        }
        items(items = children) { child ->
            val childDevices = viewModel.loginResponse?.data?.devices?.filter { it.childId == child.childId }
            val allChildDevicesActive = childDevices?.all { monitorStates[it.childId] == true } == true

            val duration = remember { mutableIntStateOf(0) }

            LaunchedEffect(Unit) {
                while (true) {
                    val seconds = viewModel.getMonitorDurationSeconds(child.childId)
                    duration.value = (seconds ?: 0).toInt()
                    delay(1000)
                }
            }

            val durationText = formatDuration(duration.value)

            Box(modifier = Modifier.padding(20.dp)) {
                SwipeableButton(
                    time = durationText,
                    navController = navController,
                    viewModel = viewModel,
                    name = child.childName,
                    childId = child.childId,
                    userId = userId,
                    childIcon = child.childIcon,
                    onOrOff = allChildDevicesActive,  // Pass the derived state
                    onClick = {
                        navController.navigate(
                            Screens.ChildSettingsMenu(
                                child.childId,
                                child.childIcon,
                                child.childName,
                            )
                        )
                    }
                )
            }
        }
    }

    if (viewModel.isonLoading) {
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
fun SwipeableButton(
    time: String?,
    navController: NavController,
    viewModel: AppViewModel,
    name : String,
    childId : Long,
    userId : Long,
    childIcon : String?,
    onOrOff : Boolean,
    onClick: () -> Unit
) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    var popupState by remember { mutableStateOf(false) }

    val anchors = mapOf(0f to 0, -200f to 1) // Define anchor points for swipe

    val iconPainter = when(childIcon){
        "0" -> painterResource(R.drawable.blue_round_01)
        "1" -> painterResource(R.drawable.pink_round_01)
        "2" -> painterResource(R.drawable.orange_round_01)
        "3" -> painterResource(R.drawable.pink_round_01)
        "4" -> painterResource(R.drawable.purple_round_01)
        "5" -> painterResource(R.drawable.yellow_round_01)
        else -> painterResource(R.drawable.blue_round_01)

    }

    val onColor = when(childIcon){
        "0" -> Color(0xFF41B5FF)
        "1" -> Color(0xFFFF89CA)
        "2" -> Color(0xFFFF7D3B)
        "3" -> Color(0xFFFF89CA)
        "4" -> Color(0xFFA667FF)
        "5" -> Color(0xFFFFD048)
        else -> Color(0xFF41B5FF)
    }

    LaunchedEffect(Unit) {
        swipeableState.snapTo(0)
    }

    if(popupState){
        DeletePopup(onDismissRequest = {popupState = false},childId = childId, userId = userId, viewModel = viewModel)
    }
    if(viewModel.editChildProfilePopupState){
        EditProfilePopUp(onDismiss = { viewModel.editChildProfilePopupState = false },
            onConfirm = {},
            userId = userId,
            childId = childId,
            viewModel = viewModel,
            childName = name
        )
    }

    Column(
        modifier = Modifier
            .background(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(Color.Transparent) // Background behind swipeable content
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    orientation = Orientation.Horizontal,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) }
                )
        ) {
            // Background Actions (Edit & Delete)
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.background(Color.Transparent)
                ){
                    Text("Edit", modifier = Modifier.clickable {
                        viewModel.editChildProfilePopupState = true
                    }, color = Color.Black)
                    HorizontalDivider(modifier = Modifier.width(50.dp))
                    Text("Delete", modifier = Modifier.clickable {
                        popupState = true
                    }, color = Color.Red)
                }
            }

            // Foreground - Swipeable Content (Does not shrink)
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .offset {
                        IntOffset(
                            swipeableState.offset.value.roundToInt(),
                            0
                        )
                    } // Moves left/right
                    .clip(RoundedCornerShape(50.dp))
                    .background(color = Color.Transparent)
                    .padding(horizontal = 16.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {
                // User Icon (Fixed size, does NOT shrink)
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ){
                    if (time != null) {
                        CustomActionButton(
                            navController = navController,
                            modifier = Modifier,
                            onClick = onClick,
                            viewModel = viewModel,
                            name = name,
                            imagePainter = iconPainter,
                            onColor = onColor,
                            childId = childId,
                            time = time,
                            initialState = onOrOff
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DeletePopup(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    childId: Long,
    userId: Long,
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
                onClick = { viewModel.deleteChild(userId,childId, context)}
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

@Composable
fun EditProfilePopUp(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    viewModel: AppViewModel,
    childId: Long,
    userId: Long,
    childName : String
){
    val context = LocalContext.current
    Dialog(
        onDismissRequest = {onDismiss()},
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxWidth(0.95f)
        )
        {
            OutlinedTextField(
                value = viewModel.childNameInput?:childName,
                onValueChange = {viewModel.childNameInput = it},
                label = { Text("Child's Name", modifier = Modifier.fillMaxWidth()) },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp)
            )
            Box(modifier = Modifier.padding(horizontal = 50.dp)){
                Column {
                    Row {
                        SelectableIcon(R.drawable.blue_round_01, index = "0", viewModel.selectedIndex) { viewModel.selectedIndex = it }
                        SelectableIcon(R.drawable.pink_round_01, index = "1", viewModel.selectedIndex) { viewModel.selectedIndex = it }
                        SelectableIcon(R.drawable.orange_round_01, index = "2", viewModel.selectedIndex) { viewModel.selectedIndex = it }
                    }
                    Row {
                        SelectableIcon(R.drawable.pink_round_01, index = "3", viewModel.selectedIndex) { viewModel.selectedIndex = it }
                        SelectableIcon(R.drawable.purple_round_01, index = "4", viewModel.selectedIndex) { viewModel.selectedIndex = it }
                        SelectableIcon(R.drawable.yellow_round_01, index = "5", viewModel.selectedIndex) { viewModel.selectedIndex = it }
                    }
                }
            }
            Button(
                onClick = {viewModel.changeChildProfileSettings(userId,childId,context,childName)},
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ){
                Text("CONFIRM")
            }

        }
    }
}

@Composable
fun SelectableIcon(
    resId: Int,
    index: String,
    selectedIndex: String?,
    onSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .size(80.dp)
            .border(
                width = if (selectedIndex == index) 3.dp else 0.dp,
                color = if (selectedIndex == index) Color.Yellow else Color.Transparent,
                shape = CircleShape
            )
            .clickable(
                onClick = { onSelected(index) },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(resId),
            contentDescription = "Edit",
            tint = Color.Unspecified,
            modifier = Modifier.size(80.dp)
        )
    }
}
