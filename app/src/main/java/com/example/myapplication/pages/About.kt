package com.example.myapplication.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.AppViewModel

@Composable
fun About(
    navController: NavHostController,
    viewModel: AppViewModel,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier.padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        OutlinedButton(
            onClick = {}, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Terms of Service")
        }
        OutlinedButton(
            onClick = {}, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Privacy Policy")
        }
        OutlinedButton(
            onClick = {}, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Rate the App")
        }
    }
}
