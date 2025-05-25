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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CustomActionButton(
    modifier: Modifier,
    navController: NavController,
    onClick: (() -> Unit)?,
    viewModel: AppViewModel,
    name: String?,
    imagePainter: Painter,
    onColor: Color,
    childId: Long?,
    time: String,
    initialState: Boolean
) {
    // Get monitor state from ViewModel (should return State<Boolean> or similar)
    val monitorStates by viewModel.monitorStates.collectAsState()
    val isOnState = childId?.let { monitorStates[it] } ?: initialState
    val currentColor = if (isOnState) onColor else Color(0xFF0f1a38)

    Card(
        onClick = onClick ?: {},
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(
            containerColor = currentColor
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .padding(start = 10.dp)
            )
            Column(
                modifier = Modifier.weight(1.0f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = name ?: "Name", color = Color.White)
                Text(text = time, modifier = Modifier.padding(top = 16.dp), color = Color.White)
            }
            Image(
                painter = painterResource(R.drawable.power),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .padding(end = 16.dp)
                    .clickable {
                        val newState = !isOnState
                        childId?.let {
                            viewModel.toggleMonitor(newState, it)
                            viewModel.setChildState(it, newState)
                        }
                    }
            )
        }
    }
}
