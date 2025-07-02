package com.example.myapplication.pages

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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun Devices(
    navController: NavHostController,
    paddingValues: PaddingValues
){
    val parentDevices = listOf("Device 1", "Device 2", "Device 3")
    val childDevices = listOf("Child Device 1", "Child Device 2", "Child Device 3")

    DeviceListScreen(
        parentDevices = parentDevices,
        childDevices = childDevices,
        paddingValues = paddingValues
    )
}

@Composable
fun DeviceListScreen(
    parentDevices: List<String>,
    childDevices: List<String>,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues =paddingValues)
            .padding(24.dp)
    ) {
        // Top Bar with Edit
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Edit",
                color = Color(0xFF4059AD),
                fontSize = 16.sp,
                modifier = Modifier.clickable {}
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Parent Devices
        Column(
            modifier = Modifier.padding(24.dp).fillMaxSize()
        ){
            Text(
                text = "Parent Devices",
                color = Color(0xFF4059AD),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))

            parentDevices.forEach { device ->
                Text(
                    text = device,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 12.dp, bottom = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Child Devices
            Text(
                text = "Child Devices",
                color = Color(0xFF4059AD),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))

            childDevices.forEach { device ->
                Text(
                    text = device,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 12.dp, bottom = 4.dp)
                )
            }
        }
    }
}
