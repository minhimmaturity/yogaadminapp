package com.example.yoga_app.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.yoga_app.component.Footer
import com.example.yoga_app.component.Header

@Composable
fun SettingScreen(modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current
    val isCreatingClass = false
    Scaffold(
        modifier = modifier,
        topBar = { Header(navController, context, title = "Setting") },
        bottomBar = { Footer(navController, isCreatingClass, "") }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Setting")
        }
    }
}