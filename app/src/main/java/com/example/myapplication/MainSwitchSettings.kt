package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.Icons.Power_settings_circle

@Composable
fun MainSwitchSettings(
    modifier: Modifier,
    navController: NavHostController,
    toggleRequest : ToggleRequest,
    viewModel: AppViewModel
) {
    Scaffold(
        topBar = { NavTopBar(modifier = Modifier, navController) },
        bottomBar = {
            Button(
                onClick = {
                    Log.d("STATUS", toggleRequest.toString())
                    viewModel.ToggleRequestInfo = toggleRequest
                    viewModel.ToggleChildSettings()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp) // Add padding around the button
            ) {
                Text(text = "SAVE")
            }
        }
    ) { value ->
        Column(
            modifier = Modifier.padding(value)
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
                            Text("MAIN SWITCH SETTINGS", modifier = Modifier.padding(start = 16.dp), color = Color.White)
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    var gaming by remember {mutableStateOf(true)}
                    Text(text = "Gaming", modifier = Modifier.padding(horizontal = 30.dp))
                    Switch(
                        checked = gaming,
                        onCheckedChange = {
                            gaming = it
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
                    toggleRequest.gamingBlocked = gaming
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    var socialMedia by remember {mutableStateOf(true)}
                    Text(text = "Social Media", modifier = Modifier.padding(horizontal = 30.dp))
                    Switch(
                        checked = socialMedia,
                        onCheckedChange = {
                            socialMedia = it
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
                    toggleRequest.socialMediaBlocked = socialMedia
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    var youtube by remember {mutableStateOf(true)}
                    Text(text = "Youtube", modifier = Modifier.padding(horizontal = 30.dp))
                    Switch(
                        checked = youtube,
                        onCheckedChange = {
                            youtube = it
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
                    toggleRequest.youTubeBlocked = youtube
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

//@Preview
//@Composable
//fun MainSwitchSettingsPreview() {
//    MainSwitchSettings(modifier = Modifier, navController = rememberNavController())
//}

