package com.example.myapplication.pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.AppViewModel
import com.example.myapplication.R
import com.example.myapplication.navigation.Screens

@Composable
fun AddChildProfile(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: AppViewModel,
    navController: NavController
) {

    val deleteResultState by viewModel.deleteResult.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(deleteResultState) {
        deleteResultState?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearDeleteResult()
            navController.navigate(Screens.Home)
        }
    }


    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            Text("Create a Profile for your child")
        }
        item{
            viewModel.childNameInput?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = {viewModel.childNameInput = it},
                    label = { Text("Child's Name", modifier = Modifier.fillMaxWidth()) },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(50.dp)
                )
            }
        }
        item {
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
        }
        item {
            Button(
                onClick = {viewModel.addChildProfile(context = context)}
            ) {
                Text("Confirm")
            }
        }
    }
}