package com.example.myapplication.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.myapplication.AppViewModel
import com.example.myapplication.Child
import com.example.myapplication.icons.Power_settings_circle
import com.example.myapplication.ToggleRequest
import com.example.myapplication.navigation.Screens

@Composable
fun ChildSettings(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: AppViewModel,
    paddingValues: PaddingValues,
    args: Screens.ChildSettings
) {
    val deleteResult by viewModel.deleteResult.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.refreshChildById(context, args.childId) { child ->
                    child?.let {
                        viewModel.updateToggleState(
                            gaming = it.gamingBlocked,
                            socialMedia = it.socialMediaBlocked,
                            youTube = it.youTubeBlocked
                        )
                    }
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    LaunchedEffect(deleteResult) {
        deleteResult?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearDeleteResult()
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
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            imageVector = Power_settings_circle,
                            contentDescription = null,
                            modifier = Modifier
                                .size(70.dp)
                                .padding(start = 16.dp)
                        )
                        Text(
                            "MAIN SWITCH SETTINGS",
                            modifier = Modifier.padding(start = 16.dp),
                            color = Color.White
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Gaming", modifier = Modifier.padding(horizontal = 30.dp))
                Switch(
                    checked = viewModel.toggleState.gamingBlocked,
                    onCheckedChange = {
                        viewModel.updateToggleState(gaming = it)
                        viewModel.toggleChildSettings(args.childId)
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Social Media", modifier = Modifier.padding(horizontal = 30.dp))
                Switch(
                    checked = viewModel.toggleState.socialMediaBlocked,
                    onCheckedChange = {
                        viewModel.updateToggleState(socialMedia = it)
                        viewModel.toggleChildSettings(args.childId)
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Youtube", modifier = Modifier.padding(horizontal = 30.dp))
                Switch(
                    checked = viewModel.toggleState.youTubeBlocked,
                    onCheckedChange = {
                        viewModel.updateToggleState(youTube = it)
                        viewModel.toggleChildSettings(args.childId)
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
            Spacer(modifier = Modifier.height(16.dp))
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .clickable {
                    navController.navigate(Screens.ScreenTimeSettings)
                },
            shape = RoundedCornerShape(50.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF0f1a38),
            ),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                Text(
                    "SCREEEN TIME SETTINGS",
                    modifier = Modifier.padding(start = 16.dp),
                    color = Color.White
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



