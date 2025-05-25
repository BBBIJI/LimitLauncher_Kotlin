package com.example.myapplication.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.AppViewModel
import com.example.myapplication.R

@Composable
fun CustomerSupport(
    navController: NavHostController,
    viewModel: AppViewModel,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(R.drawable.adobe_express___file),
                contentDescription = null,
                modifier = Modifier.size(300.dp)
            )
        }
        OutlinedButton(
            onClick = {},
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxWidth()
        ) {
            Text("FAQ")
        }
        OutlinedButton(
            onClick = {},
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxWidth()
        ) {
            Text("Contact Us")
        }
        OutlinedButton(
            onClick = {},
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxWidth()
        ) {
            Text("Instructional Videos")
        }
        OutlinedButton(
            onClick = {},
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxWidth()
        ) {
            Text("App Guide")
        }
    }
}