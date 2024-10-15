package com.example.yoga_app.view

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.yoga_app.component.Login

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController) {
    Login(modifier, navController)
}

