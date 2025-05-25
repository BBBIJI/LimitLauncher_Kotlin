package com.example.myapplication.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.myapplication.AppViewModel
import com.example.myapplication.R
import com.example.myapplication.ToggleRequest
import com.example.myapplication.auth.LoginScreen
import com.example.myapplication.auth.RegisterScreen
import com.example.myapplication.pages.About
import com.example.myapplication.pages.AddChildDevice
import com.example.myapplication.pages.AddChildProfile
import com.example.myapplication.pages.ChildSettings
import com.example.myapplication.pages.ChildSettingsMenu
import com.example.myapplication.pages.CustomerSupport
import com.example.myapplication.pages.Devices
import com.example.myapplication.pages.LanguagePage
import com.example.myapplication.pages.MainPage
import com.example.myapplication.pages.Password
import com.example.myapplication.pages.Personal
import com.example.myapplication.pages.ScreenTimeSettings
import com.example.myapplication.pages.Subscription
import com.example.myapplication.pages.SubscriptionLearnMore
import kotlinx.coroutines.launch

val gradientBrush = Brush.linearGradient(
    colors = listOf(Color(0xFF3f66a7), Color(0xFF0c1f3f)),
    start = Offset(0f, 0f),
    end = Offset(1000f, 1000f)
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationGraph(
    viewModel: AppViewModel,
    navController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    var expanded by remember { mutableStateOf(false) }

    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet(
            drawerContainerColor = Color.Transparent,
            modifier = Modifier
                .background(gradientBrush)
                .width(350.dp)
        ) {
            Row {
                IconButton(
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.info),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            }
            Text("Hi, Fred", modifier = Modifier.padding(16.dp), color = Color.White)

            Row(
                modifier = Modifier.clickable { expanded = !expanded }
            ) {
                Icon(
                    painter = painterResource(R.drawable.account),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "My Account",
                    modifier = Modifier.padding(16.dp),
                    color = Color.White
                )
            }
            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(start = 32.dp)) {
                    Text(text = "• Personal",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                scope.launch {
                                    navController.navigate(Screens.Personal)
                                    drawerState.close()
                                }
                            })
                    Text(text = "•" +
                            " Password",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                scope.launch {
                                    navController.navigate(Screens.Password)
                                    drawerState.close()
                                }
                            })
                    Text(text = "• Subscription",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                scope.launch {
                                    navController.navigate(Screens.Subscription)
                                    drawerState.close()
                                }
                            })
                }
            }
            Row(
                modifier = Modifier.clickable {
                    scope.launch {
                        navController.navigate(Screens.About)
                        drawerState.close()
                    }
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.untitled_design__1___1_),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "About",
                    modifier = Modifier.padding(16.dp),
                    color = Color.White
                )
            }
            Row(
                modifier = Modifier.clickable {
                    scope.launch {
                        navController.navigate(Screens.CustomerSupport)
                        drawerState.close()
                    }
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.customer_support__1_),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Customer Support",
                    modifier = Modifier.padding(16.dp),
                    color = Color.White
                )
            }
            Row(
                modifier = Modifier.clickable {
                    scope.launch {
                        navController.navigate(Screens.Language)
                        drawerState.close()
                    }
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.language),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Language",
                    modifier = Modifier.padding(16.dp),
                    color = Color.White
                )
            }
            Row(
                modifier = Modifier.clickable {
                    scope.launch {
                        navController.navigate(Screens.Devices)
                        drawerState.close()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Devices",
                    modifier = Modifier.padding(16.dp),
                    color = Color.White
                )
            }
            Spacer(Modifier.weight(2f))
            Row {
                Icon(
                    painter = painterResource(R.drawable.log_out),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
                Text("Log Out", color = Color.White, modifier = Modifier.padding(16.dp))
            }
        }
    }, drawerState = drawerState) {
        Scaffold(
            topBar = {
                if (viewModel.loginState) {
                    CenterAlignedTopAppBar(
                        modifier = Modifier
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xff4667a3), Color(0xff0e1f3b))
                                )
                            )
                            .height(130.dp),
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                        navigationIcon = {
                            when (currentBackStackEntry?.destination?.route) {
                                Screens.Home::class.qualifiedName -> { // Use class name directly
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(start = 16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        IconButton(
                                            onClick = {
                                                scope.launch { drawerState.open() }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Menu,
                                                contentDescription = "Menu",
                                                modifier = Modifier.size(40.dp),
                                                tint = Color.White
                                            )
                                        }
                                    }
                                }

                                else -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(start = 16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        IconButton(
                                            onClick = { navController.navigateUp() }
                                        ) {
                                            Icon(
                                                modifier = Modifier.size(30.dp),
                                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = null,
                                                tint = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        },
                        title = {
                            when (currentBackStackEntry?.destination?.route) {
                                Screens.About::class.qualifiedName -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "About", color = Color.White, fontSize = 40.sp)
                                    }
                                }

                                Screens.Personal::class.qualifiedName -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Personal",
                                            color = Color.White,
                                            fontSize = 40.sp
                                        )
                                    }
                                }

                                Screens.Password::class.qualifiedName -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Password",
                                            color = Color.White,
                                            fontSize = 40.sp
                                        )
                                    }
                                }

                                Screens.Subscription::class.qualifiedName -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Subscription",
                                            color = Color.White,
                                            fontSize = 40.sp
                                        )
                                    }
                                }

                                Screens.CustomerSupport::class.qualifiedName -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Customer Support",
                                            color = Color.White,
                                            fontSize = 30.sp
                                        )
                                    }
                                }

                                else -> {
                                    Icon(
                                        modifier = Modifier.size(200.dp),
                                        painter = painterResource(id = R.drawable.limitlauncherlogo),
                                        contentDescription = "App Logo",
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                        }
                    )
                }
            }
        ) { paddingvalues ->
            NavHost(
                navController = navController,
                startDestination = Graphs.AuthGraph
            ) {
                navigation<Graphs.AuthGraph>(
                    startDestination = Screens.SignIn
                ) {
                    composable<Screens.SignIn> {
                        SignInScreen(navController, viewModel)
                    }
                    composable<Screens.Login> {
                        LoginScreen(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    composable<Screens.Register> {
                        RegisterScreen(navController, viewModel)
                    }
                }
                navigation<Graphs.MainGraph>(
                    startDestination = Screens.Home
                ) {
                    composable<Screens.Home> {
                        MainPage(viewModel, navController, paddingvalues)
                    }
                    composable<Screens.ChildSettingsMenu> {
                        val args = it.toRoute<Screens.ChildSettingsMenu>()
                        ChildSettingsMenu(Modifier, navController, viewModel, paddingvalues, args)
                    }
                    composable<Screens.ChildSettings> {
                        val args = it.toRoute<Screens.ChildSettings>()
                        ChildSettings(
                            Modifier,
                            navController,
                            viewModel,
                            paddingvalues,
                            args
                        )
                    }
                    composable<Screens.About> {
                        About(navController, viewModel, paddingvalues)
                    }
                    composable<Screens.Personal> {
                        Personal(navController, viewModel, paddingvalues)
                    }
                    composable<Screens.Password> {
                        Password(navController, viewModel, paddingvalues)
                    }
                    composable<Screens.Subscription> {
                        Subscription(navController, viewModel, paddingvalues)
                    }
                    composable<Screens.SubscriptionLearnMore> {
                        SubscriptionLearnMore(paddingvalues, navController)
                    }
                    composable<Screens.CustomerSupport> {
                        CustomerSupport(navController, viewModel, paddingvalues)
                    }
                    composable<Screens.Language> {
                        LanguagePage(navController, viewModel, paddingvalues)
                    }
                    composable<Screens.Devices> {
                        Devices(navController, paddingvalues)
                    }
                    composable<Screens.AddChildDevice> {
                        AddChildDevice(
                            paddingvalues = paddingvalues,
                            modifier = Modifier
                        )
                    }
                    composable<Screens.ScreenTimeSettings> {
                        ScreenTimeSettings(
                            modifier = Modifier,
                            paddingValues = paddingvalues,
                            navController = navController
                        )
                    }
                    composable<Screens.AddChildProfile> {
                        AddChildProfile(
                            modifier = Modifier,
                            paddingValues = paddingvalues,
                            viewModel = viewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}





