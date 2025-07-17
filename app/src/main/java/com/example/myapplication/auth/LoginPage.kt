package com.example.myapplication.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.AppViewModel
import com.example.myapplication.navigation.Screens
import kotlinx.coroutines.CoroutineScope

@Composable
fun LoginScreen(navController: NavHostController, viewModel: AppViewModel) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(Screens.Home)
            viewModel.isDrawer = true
        }
    }

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
        LoginCard(navController, viewModel)
    }
}

@Composable
fun LoginCard(navController: NavHostController, viewModel: AppViewModel) {
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
            LoginHeader(navController)
            Spacer(modifier = Modifier.height(24.dp))
            LoginForm(viewModel, navController)
        }
    }
}

@Composable
fun LoginHeader(
    navController: NavHostController
) {
    Box( // Use a Box as the main container
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF3f66a7), Color(0xFF0c1f3f)),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            )
            .height(80.dp)
    ) {
        // Back Button aligned to the start
        IconButton(
            modifier = Modifier
                .align(Alignment.CenterStart) // Align to the start of the Box
                .padding(horizontal = 16.dp),
            onClick = {navController.navigate(Screens.SignIn)}
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        // Text centered in the Box
        Text(
            text = "LOGIN",
            fontSize = 30.sp,
            style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center) // Align to the center of the Box
        )
    }
}
@Composable
fun LoginForm(viewModel: AppViewModel, navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 40.dp)
    ) {
        CustomTextField(value = viewModel.emailInput, label = "E-mail", icon = Icons.Default.Email) {
            viewModel.emailInput = it
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            value = viewModel.passwordInput,
            label = "Password",
            icon = Icons.Default.Lock,
            isPassword = true
        ) {
            viewModel.passwordInput = it
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Forgot Your Password?",
            color = Color(0xFF0D47A1),
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 32.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        LoginButton(navController, viewModel, coroutineScope)
        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = {navController.navigate(Screens.Register)}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Don't Have an Account? Register Now!",color = Color(0xFF0D47A1), modifier = Modifier.clickable {
                navController.navigate(Screens.Register)
            })
        }
    }
}

@Composable
fun CustomTextField(value: String, label: String, icon: ImageVector, isPassword: Boolean = false, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(imageVector = icon, contentDescription = "$label Icon") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        shape = RoundedCornerShape(50.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun LoginButton(navController: NavHostController, viewModel: AppViewModel, coroutineScope: CoroutineScope) {
    val responseMessage = viewModel.responseMessage
    val context = LocalContext.current


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                viewModel.loading = true
                viewModel.login(context)
                if(viewModel.loginState){
                    navController.navigate(Screens.Home)
                    viewModel.isDrawer = true
                }
                viewModel.refreshChildren(context)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF3f66a7), Color(0xFF0c1f3f))
                    ), shape = ButtonDefaults.shape
                ),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.White),
            enabled = !viewModel.loading
        ) {
            if (viewModel.loading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(text = "LOGIN", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = responseMessage, color = Color.Red, fontSize = 14.sp)
    }
}
