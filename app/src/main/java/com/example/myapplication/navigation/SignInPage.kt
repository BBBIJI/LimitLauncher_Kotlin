package com.example.myapplication.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.AppViewModel

@Composable
fun SignInScreen(navController: NavHostController, viewModel: AppViewModel) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

//    LaunchedEffect(isLoggedIn) {
//        if (isLoggedIn) {
//            navController.navigate("MainPage") {
//                popUpTo("LoginScreen") { inclusive = true } // Prevents going back to login
//            }
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF2f538c), Color(0xFF365b8f)),
                    start = Offset(0f, 0f),
                    end = Offset(0f, 1000f)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignInCard(navController,viewModel)
    }
}

@Composable
fun SignInCard(navController: NavHostController, viewModel: AppViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.9f),
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignInHeader()
            Box(
                modifier = Modifier.padding(top = 40.dp, start = 32.dp, end = 32.dp, bottom = 20.dp)
            ){
                Column{
                    SignInWithGoogle()
                    SignInWithApple()
                    SignInWithEmail(navController)
                    Text("or", modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp))
                    LinkNewParentDevice()
                    TextButton(onClick = {navController.navigate(Screens.Register)}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Text("Don't Have an Account? Register Now!",color = Color(0xFF0D47A1),)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Continue if you agree to our", modifier = Modifier.align(Alignment.CenterHorizontally), fontSize = 14.sp,lineHeight = 14.sp)
                    Text("Terms & Conditions and Privacy Policy",modifier = Modifier.align(Alignment.CenterHorizontally),fontSize = 14.sp,lineHeight = 14.sp)
                }
            }
        }
    }
}

@Composable
fun SignInHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF3f66a7), Color(0xFF0c1f3f)),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            )
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(50.dp))
        Text(
            text = "SIGN IN",
            fontSize = 30.sp,
            style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(60.dp))
    }
}

@Composable
fun SignInWithGoogle(){
    Button(onClick = {}, colors = ButtonColors(
        containerColor = Color(0xFFef6b5a),
        contentColor = Color.White,
        disabledContainerColor = Color(0xFFef6b5a),
        disabledContentColor = Color.White
    ), modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        Text("Sign In with Google")
    }
}

@Composable
fun SignInWithApple(){
    Button(onClick = {}, colors = ButtonColors(
        containerColor = Color(0xFF7d7c7c),
        contentColor = Color.White,
        disabledContainerColor = Color(0xFF7d7c7c),
        disabledContentColor = Color.White
    ), modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        Text("Sign In with Apple")
    }
}

@Composable
fun SignInWithEmail(
    navController: NavHostController
){
    OutlinedButton(onClick = {navController.navigate(Screens.Login)}, colors = ButtonColors(
        containerColor = Color.Transparent,
        contentColor = Color.Black,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = Color.Black
    ), modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        Text("Sign In with Email")
    }
}

@Composable
fun LinkNewParentDevice(){
    OutlinedButton(onClick = {}, colors = ButtonColors(
        containerColor = Color.Transparent,
        contentColor = Color.Black,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = Color.Black
    ), modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        Text("Link a New Parent Device")
    }
}