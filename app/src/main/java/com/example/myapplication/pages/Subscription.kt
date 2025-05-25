package com.example.myapplication.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import com.example.myapplication.AppViewModel
import com.example.myapplication.navigation.Screens

@Composable
fun Subscription(
    navController: NavHostController,
    viewModel: AppViewModel,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier.padding(paddingValues).verticalScroll(rememberScrollState())
    ) {
        Text("Your Subscription", modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 16.dp))
        OutlinedButton(
            modifier = Modifier.padding(start = 32.dp, end = 32.dp).fillMaxWidth(),
            onClick = {}
        ) {
            Text("Free")
        }
        Box(modifier = Modifier.padding(top = 2.dp, start = 32.dp, end = 32.dp).fillMaxWidth()){
            Text("Upgrade", modifier = Modifier.align(Alignment.CenterEnd).clickable {
                navController.navigate(Screens.SubscriptionLearnMore)
            }, fontSize = 12.sp)
        }
        Text("Subscription Tiers:", modifier = Modifier.padding(top = 16.dp, start = 32.dp, end = 32.dp))
        ExpandingMenu1()
        ExpandingMenu2()
        ExpandingMenu3()
        ExpandingMenu4(navController)
    }
}

@Composable
fun ExpandingMenu1() {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .background(Color(0xFF3F5BA9), RoundedCornerShape(40.dp))
            .clickable(indication = null,interactionSource = remember { MutableInteractionSource() }) { expanded = !expanded }
            .padding(16.dp)
    ) {

        Text("Free", color = Color.White.copy(alpha = 0.8f), fontSize = 30.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(8.dp))

        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)) {
                HorizontalDivider(thickness = 1.dp)
                Box(modifier = Modifier.padding(top = 16.dp))
                Text("• Manage 1 device", color = Color.White, fontSize = 16.sp)
                Text("• Block Gaming on any device", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun ExpandingMenu2() {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .background(Color(0xFF3F5BA9), RoundedCornerShape(40.dp))
            .clickable(indication = null,interactionSource = remember { MutableInteractionSource() }) { expanded = !expanded }
            .padding(16.dp)
    ) {

        Text("Basic", color = Color.White.copy(alpha = 0.8f), fontSize = 30.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(8.dp))

        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)) {
                HorizontalDivider(thickness = 1.dp)
                Box(modifier = Modifier.padding(top = 16.dp))
                Text("• Manage up to 10 devices", color = Color.White, fontSize = 16.sp)
                Text("• Group your devices by child", color = Color.White, fontSize = 16.sp)
                Text("• Cumulative screen time across all devices", color = Color.White, fontSize = 16.sp)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        onClick = {}
                    ) {
                        Text("Learn More", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandingMenu3() {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .background(Color(0xFF3F5BA9), RoundedCornerShape(40.dp))
            .clickable(indication = null,interactionSource = remember { MutableInteractionSource() }) { expanded = !expanded }
            .padding(16.dp)
    ) {

        Text("Plus", color = Color.White.copy(alpha = 0.8f), fontSize = 30.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(8.dp))

        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)) {
                HorizontalDivider(thickness = 1.dp)
                Box(modifier = Modifier.padding(top = 16.dp))
                Text("• Manage up to 15 devices", color = Color.White, fontSize = 16.sp)
                Text("• Group your devices by child", color = Color.White, fontSize = 16.sp)
                Text("• Cumulative screen time across all devices", color = Color.White, fontSize = 16.sp)
                Text("• Mandatory break times from devices", color = Color.White, fontSize = 16.sp)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        onClick = {}
                    ) {
                        Text("Learn More", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandingMenu4(navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .background(Color(0xFF3F5BA9), RoundedCornerShape(40.dp))
            .clickable(indication = null,interactionSource = remember { MutableInteractionSource() }) { expanded = !expanded }
            .padding(16.dp)
    ) {

        Text("Premium", color = Color.White.copy(alpha = 0.8f), fontSize = 30.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(8.dp))

        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)) {
                HorizontalDivider(thickness = 1.dp)
                Box(modifier = Modifier.padding(top = 16.dp))
                Text("• Manage up to 15 devices", color = Color.White, fontSize = 16.sp)
                Text("• Group your devices by child", color = Color.White, fontSize = 16.sp)
                Text("• Cumulative screen time across all devices", color = Color.White, fontSize = 16.sp)
                Text("• Mandatory break times from devices", color = Color.White, fontSize = 16.sp)
                Text("• 2+ Parent devices", color = Color.White, fontSize = 16.sp)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        onClick = {navController.navigate(Screens.SubscriptionLearnMore)}
                    ) {
                        Text("Learn More", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun SubscriptionLearnMore(
    paddingValues: PaddingValues,
    navController: NavHostController
){
    Column(
        modifier = Modifier.padding(paddingValues).verticalScroll(rememberScrollState())
    ) {
        var selectedOption by remember { mutableStateOf("Monthly") }

        SmoothToggleButton(
            selectedOption = selectedOption,
            onOptionSelected = { selectedOption = it }
        )
        ExpandingMenu1()
        ExpandingMenu2()
        ExpandingMenu3()
        ExpandingMenu4(navController)
    }
}

@Composable
fun SmoothToggleButton(
    options: List<String> = listOf("Monthly", "Yearly"),
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val selectedIndex = options.indexOf(selectedOption)

    // Animate the position of the moving indicator
    val indicatorOffset by animateFloatAsState(
        targetValue = selectedIndex.toFloat(),
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "toggle_animation"
    )

    Box(
        modifier = Modifier.padding(32.dp)
    ){
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(Color(0xFF0E1B3D)) // Dark navy background
                .padding(4.dp)
                .height(40.dp) // Ensure height consistency
        ) {
            // Moving selection indicator
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(170.dp) // Width of each option
                    .offset(x = indicatorOffset * 170.dp) // Moves smoothly
                    .clip(RoundedCornerShape(50))
                    .background(Color.White) // White indicator for selected option
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                options.forEachIndexed { index, option ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable { onOptionSelected(option) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option,
                            color = if (selectedIndex == index) Color(0xFF0E1B3D) else Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
