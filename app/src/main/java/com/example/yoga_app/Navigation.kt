package com.example.yoga_app

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yoga_app.view.CenterAlignedTopAppBarExample
import com.example.yoga_app.view.CreateScreen
import com.example.yoga_app.view.DetailScreen
import com.example.yoga_app.view.LoginScreen
import com.example.yoga_app.view.SettingScreen

sealed class Routes(val route: String) {
    data object Login: Routes("Login")
    data object Home : Routes("Home")
    data object Create : Routes("Create")
    data object Setting : Routes("Setting")
    data object Detail : Routes("Detail/{id}")
    data object Edit: Routes("Edit/{courseId}")
}

@Composable
fun Navigation(modifier: Modifier = Modifier, context: Context){
    val navController = rememberNavController()
    val startDestination = if (isUserLoggedIn(context)) Routes.Home.route else Routes.Login.route
    NavHost(navController = navController, startDestination = startDestination) {

        composable(Routes.Login.route) {
            LoginScreen(modifier, navController = navController)
        }
        composable(Routes.Home.route) {
            CenterAlignedTopAppBarExample(modifier, navController = navController)
        }
        composable(Routes.Setting.route) {
            SettingScreen(modifier, navController = navController)
        }
        composable(Routes.Create.route) {
            CreateScreen(modifier, navController = navController, "")
        }
        composable(Routes.Edit.route) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId")
            CreateScreen(modifier, navController = navController, courseId)
        }
            composable(Routes.Detail.route) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("id")
            DetailScreen(modifier, navController = navController, courseId)
        }
    }
}

fun isUserLoggedIn(context: Context): Boolean {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    return sharedPreferences.contains("email")
}
