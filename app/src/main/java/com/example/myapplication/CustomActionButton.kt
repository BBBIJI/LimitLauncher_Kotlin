package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.Icons.Power_settings_circle

@Composable
fun CustomActionButton(
    modifier: Modifier,
    navController: NavHostController?,
    onClick: (() -> Unit)?,
    viewModel: AppViewModel
) {
    var state by remember { mutableStateOf(false) }
    val currentColor = remember { mutableStateOf(Color(0xFF0f1a38)) }
    Box(modifier = Modifier) {
        Card(
            onClick = onClick?: {},
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = RoundedCornerShape(50.dp),
            colors = CardDefaults.cardColors(
                containerColor = currentColor.value
            ),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.blue_round_01),
                    contentDescription = null,
                    modifier = Modifier
                        .size(90.dp)
                        .padding(start = 10.dp)
                )
                Column(
                    modifier = Modifier.weight(1.0f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = "KENNY", color = Color.White)
                    Text(text = "01:20", modifier = Modifier.padding(top = 16.dp),color = Color.White)
                }
                Image(
                    imageVector = Power_settings_circle,
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(end = 16.dp)
                        .clickable {
                            state= !state
                            viewModel.toggleMonitor(state)
                            Log.d("STATE", state.toString())
                            if (state) {
                                currentColor.value = Color(0xFF41B5FF)
                            } else {
                                currentColor.value = Color(0xFF0f1a38)
                            }
                        }
                )
            }
        }
    }
}