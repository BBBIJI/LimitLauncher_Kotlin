package com.example.myapplication.auth

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
fun RegisterScreen(navController: NavHostController, viewModel: AppViewModel) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

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
        RegisterCard(navController,viewModel)
    }
}

@Composable
fun RegisterCard(navController: NavHostController, viewModel: AppViewModel) {
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
            RegisterHeader()
            Spacer(modifier = Modifier.height(24.dp))
            RegisterForm(viewModel, navController)
        }
    }
}

@Composable
fun RegisterHeader() {
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
            text = "REGISTER",
            fontSize = 30.sp,
            style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(60.dp))
    }
}

@Composable
fun RegisterForm(viewModel: AppViewModel, navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        InputField(Icons.Default.Person, "First Name", value = viewModel.firstNameInput) {
            viewModel.firstNameInput = it
        }
        InputField(null, "Last Name", value = viewModel.lastNameInput) {
            viewModel.lastNameInput = it
        }
        InputField(null, "E-mail", value = viewModel.emailInput) {
            viewModel.emailInput = it
        }
        Spacer(modifier = Modifier.height(32.dp))
        InputField(Icons.Default.Lock, "Password", isPassword = true, value = viewModel.passwordInput) {
            viewModel.passwordInput = it
        }
        InputField(null, "Confirm Password", isPassword = true, value = viewModel.confirmPasswordInput) {
            viewModel.confirmPasswordInput = it
        }
        Spacer(modifier = Modifier.height(20.dp))
        RegisterButton(navController, viewModel, coroutineScope, context = LocalContext.current)
        TextButton(onClick = {navController.navigate(Screens.Login)}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Already have an account? Log In!", color = Color(0xFF0D47A1),)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text("By creating an account, you agree to our", modifier = Modifier.align(Alignment.CenterHorizontally), fontSize = 14.sp, lineHeight = 14.sp)
        Text("Terms & Condititons and Privacy Policy", modifier = Modifier.align(Alignment.CenterHorizontally), fontSize = 14.sp,lineHeight = 14.sp)

    }
}

@Composable
fun RegisterButton(
    navController: NavHostController,
    viewModel: AppViewModel,
    coroutineScope: CoroutineScope,
    context: Context
) {
    val responseMessage = viewModel.responseMessage

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                viewModel.registerUser(context) {
                    navController.navigate(Screens.Home)
                    viewModel.loginState = true
                    viewModel.isDrawer = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3f66a7),
                contentColor = Color.White
            ),
            enabled = !viewModel.loading
        ) {
            if (viewModel.loading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(text = "CREATE ACCOUNT", fontSize = 16.sp)
            }
        }

        if (responseMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(responseMessage, color = Color.Red)
        }
    }
}


@Composable
fun InputField(
    icon: ImageVector?,
    placeholder: String,
    isPassword: Boolean = false,
    value: String,
    onValueChange: (String) -> Unit
) {
    val visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None

    Row(modifier = Modifier.padding(end = 20.dp)) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.padding(16.dp))
        } else {
            Spacer(modifier = Modifier.width(54.dp))
        }
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder) },
            visualTransformation = visualTransformation,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
            ),
        )
    }
}

