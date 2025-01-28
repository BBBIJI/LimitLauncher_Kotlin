package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavTopBar(
    modifier: Modifier,
    navController: NavHostController
){
    CenterAlignedTopAppBar(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xff4667a3),
                        Color(0xff0e1f3b)
                    )
                )
            )
            .height(130.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
        navigationIcon = {
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier.fillMaxHeight()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        title = {
            Icon(
                modifier = Modifier.size(200.dp),
                painter = painterResource(id = R.drawable.limitlauncherlogo),
                contentDescription = "Image",
                tint = Color.Unspecified,
            )
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    modifier: Modifier,
    viewmodel: AppViewModel,
    navController: NavHostController
) {
    var user = viewmodel.loginResponse

    if (user != null) {
        Log.d("MainPage", user.data.userId.toString())
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { NavTopBar(modifier = Modifier,navController) }
    ) { value ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(value),
        ) {
            Card (
                modifier = Modifier.fillMaxWidth().padding(30.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                ),
            ){
                Text(
                    text = "Hello Kenny", modifier = Modifier.padding(16.dp)
                )
                CustomActionButton(
                    modifier = Modifier,
                    navController = navController,
                    onClick = {navController.navigate("ChildSettingsMenu")},
                    viewModel = viewmodel
                )
            }

//            CustomActionButton(
//                modifier = Modifier
//                    .padding(100.dp)
//            )
        }
    }
}


@Composable
fun FetchUserInfo(
    userId: Int,
    viewmodel: AppViewModel
) {
    LaunchedEffect(userId) {
        val request = UserRequest(userId)
        RetrofitInstance.api.getUserWpInfo(request).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.status == "success") {
                        viewmodel.userInfo = apiResponse.data
                    } else {
                        viewmodel.errorMessage = "Error: ${apiResponse?.status ?: "Unknown Error"}"
                    }
                } else {
                    viewmodel.errorMessage = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                viewmodel.errorMessage = "Failure: ${t.message}"
            }
        })
    }

    // Display the result
    if (viewmodel.userInfo != null) {
        Text(text = "User: ${viewmodel.userInfo!!.userFirstname} ${viewmodel.userInfo!!.userLastname}")
        Text(text = "Email: ${viewmodel.userInfo!!.userEmail}")
        Text(text = "Roles: ${viewmodel.userInfo!!.roles.joinToString()}")
        Text(text = "Subscription: ${viewmodel.userInfo!!.subscriptionName}")
    } else if (viewmodel.errorMessage.isNotEmpty()) {
        Text(text = viewmodel.errorMessage)
    } else {
        Text(text = "Loading...")
    }
}


//@Composable
//fun UserInfoScreen(modifier: Modifier) {
//
//    val viewmodel = viewModel<AppViewModel>()
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        TextField(
//            value = viewmodel.userIdInput,
//            onValueChange = { viewmodel.userIdInput = it },
//            label = { Text("Enter User ID") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(
//            onClick = { viewmodel.userId = viewmodel.userIdInput.toIntOrNull() },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Fetch User Info")
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        viewmodel.userId?.let {
//            FetchUserInfo(it)
//        }
//    }
//}

//@Composable
//fun ToggleButton(
//    modifier: Modifier
//) {
//    val viewmodel = viewModel<AppViewModel>()
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center, // Centers the content vertically
//        horizontalAlignment = Alignment.CenterHorizontally // Centers the content horizontally
//    ) {
//        Text(
//            text = viewmodel.testunit
//        )
//        Spacer(modifier = Modifier.height(16.dp)) // Adds some spacing between Text and Button
//        Button(
//            onClick = { viewmodel.toggleMonitor() }
//        ) {
//            Text(text = "Toggle")
//        }
//    }
//
//}

