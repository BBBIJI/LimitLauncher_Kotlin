package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.AppNavigationGraph
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: AppViewModel = viewModel()

            MyApplicationTheme {
                AppNavigationGraph(viewModel, navController = rememberNavController())
            }
        }
    }
}

//To-Do :
//        1. Delete Child x
//        2. Bind device to child x
//        3. add child device / Profile(today)
//        4. registration
//        5. child and mainsettings toggle fluidity(loading animation ...etc)
//        6. password forget function


    
//{
//    "username" : "helfriedst@gmail.com",
//    "password" : "4w5qlw6A1*gmGUsAVt#u*yai",
//    "uuid" : "452"
//}

//{
//    "username" : "liekenny273@gmail.com",
//    "password" : "L$sKm9l$oDj!BL0EGazASFoG",
//    "uuid" : "23523"
//}