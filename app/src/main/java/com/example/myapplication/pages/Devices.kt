package com.example.myapplication.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun Devices(
    navController: NavHostController,
    paddingValues: PaddingValues
){
    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        Button(
            onClick = {
            }
        ) { Text("Test") }
    }
}