package com.example.myapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ChildDevices(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: AppViewModel
){
    Scaffold (
        modifier = Modifier,
        topBar = { NavTopBar(modifier = Modifier, navController) },
        bottomBar = {
            Button(
                onClick = {navController.navigate("MainSwitchSettings")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp) // Add padding around the button
            ) {
                Text(text = "CHILD SETTINGS MENU")
            }
        }
    ){
        value ->
        Column(
            modifier = Modifier.padding(value)
        ){
                Card (
                    modifier = Modifier.fillMaxWidth().padding(30.dp),
                    shape = RoundedCornerShape(topEnd = 50.dp, topStart = 50.dp, bottomEnd = 16.dp, bottomStart = 16.dp),
                ) {
                    CustomActionButton(modifier = Modifier, null, null, viewModel)
                    Text(text = "Child 1", modifier = Modifier.padding(16.dp))

                }
        }
    }
}