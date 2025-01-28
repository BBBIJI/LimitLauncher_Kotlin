package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                var toggleRequest = ToggleRequest()
                val viewModel: AppViewModel = viewModel()
                // Define NavHost
                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") {
                        // Use viewModel() to obtain an instance of AppViewModel
                        LoginScreenInCard(navController,viewModel)
                    }
                    composable("MainPage") {
                        MainPage(modifier = Modifier,viewModel,navController)
                    }
                    composable("ChildSettingsMenu") {
                        ChildDevices(modifier = Modifier, navController, viewModel)
                    }
                    composable("MainSwitchSettings") {
                        MainSwitchSettings(modifier = Modifier,navController, toggleRequest, viewModel)
                    }
                }
            }
        }
    }
}
