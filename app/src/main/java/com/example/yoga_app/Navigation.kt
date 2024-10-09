package com.example.yoga_app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Routes(val route: String) {
    object Home : Routes("Home")
    object Create : Routes("Create")
    object Setting : Routes("Setting")
}

@Composable
fun Navigation(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Home.route) {

        composable(Routes.Home.route) {
            CenterAlignedTopAppBarExample(modifier, navController = navController)
        }
        composable(Routes.Setting.route) {
            SettingScreen(modifier, navController = navController)
        }
        composable(Routes.Create.route) {
            CreateScreen(modifier, navController = navController)
        }
    }
}