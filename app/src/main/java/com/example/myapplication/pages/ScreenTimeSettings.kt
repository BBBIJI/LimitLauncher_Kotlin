package com.example.myapplication.pages

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
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
import androidx.wear.compose.material.Button
import com.example.myapplication.ToggleRequest

@Composable
fun ScreenTimeSettings(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavHostController,
) {
//    Column(
//        modifier = Modifier.padding(paddingValues)
//    ) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(30.dp),
//            shape = RoundedCornerShape(
//                topEnd = 50.dp,
//                topStart = 50.dp,
//                bottomEnd = 16.dp,
//                bottomStart = 16.dp
//            ),
//        ) {
//            Box(modifier = Modifier) {
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(100.dp),
//                    shape = RoundedCornerShape(50.dp),
//                    colors = CardDefaults.cardColors(
//                        containerColor = Color(0xFF0f1a38),
//                    ),
//                ) {
//                    Row(
//                        modifier = Modifier.fillMaxSize(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Absolute.Center
//                    ) {
//                        Text(
//                            "SCREEN TIME SETTINGS",
//                            modifier = Modifier.padding(start = 16.dp),
//                            color = Color.White
//                        )
//                    }
//                }
//            }
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                var mandatoryScreenTime by remember { mutableStateOf(true) }
//                Text(text = "Mandatory Screen Time", modifier = Modifier.padding(horizontal = 30.dp))
//                Switch(
//                    checked = mandatoryScreenTime,
//                    onCheckedChange = {
//                        mandatoryScreenTime = it
//                    },
//                    modifier = modifier,
//                    thumbContent = {
//                        Icon(
//                            imageVector = Icons.Filled.Check,
//                            contentDescription = null,
//                            modifier = Modifier.size(SwitchDefaults.IconSize)
//                        )
//                    }
//                )
//                toggleRequest.gamingBlocked = mandatoryScreenTime
//            }
//            Row(modifier = Modifier.padding(start = 48.dp)) {
//                Text("Break after how long")
//            }
//            Row(modifier = Modifier.padding(start = 48.dp)) {
//                Text("Break time duration")
//            }
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                var cumulativeScreenTime by remember { mutableStateOf(true) }
//                Text(text = "Cumulative Screentime", modifier = Modifier.padding(horizontal = 30.dp))
//                Switch(
//                    checked = cumulativeScreenTime,
//                    onCheckedChange = {
//                        cumulativeScreenTime = it
//                    },
//                    modifier = modifier,
//                    thumbContent = {
//                        Icon(
//                            imageVector = Icons.Filled.Check,
//                            contentDescription = null,
//                            modifier = Modifier.size(SwitchDefaults.IconSize)
//                        )
//                    }
//                )
//                toggleRequest.socialMediaBlocked = cumulativeScreenTime
//            }
//            Row(modifier = Modifier.padding(start = 48.dp)) {
//                Text("Total allowance")
//            }
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                var noScreenPeriod by remember { mutableStateOf(true) }
//                Text(text = "No Screen Period", modifier = Modifier.padding(horizontal = 30.dp))
//                Switch(
//                    checked = noScreenPeriod,
//                    onCheckedChange = {
//                        noScreenPeriod = it
//                    },
//                    modifier = modifier,
//                    thumbContent = {
//                        Icon(
//                            imageVector = Icons.Filled.Check,
//                            contentDescription = null,
//                            modifier = Modifier.size(SwitchDefaults.IconSize)
//                        )
//                    }
//                )
//                toggleRequest.youTubeBlocked = noScreenPeriod
//            }
//            Row(modifier = Modifier.padding(start = 48.dp)) {
//                Text("School hours")
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//        Spacer(modifier = Modifier.weight(0.1f))
//        Button(
//            onClick = {},
//            modifier.fillMaxWidth().padding(36.dp)
//        ) {
//            Text("Save")
//        }
//    }
}