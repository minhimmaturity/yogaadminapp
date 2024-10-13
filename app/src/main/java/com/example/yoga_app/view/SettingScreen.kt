package com.example.yoga_app.view

import android.content.Context
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
    Scaffold(
        modifier = modifier,
        topBar = { Header(navController, context) },
        bottomBar = { Footer(navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Setting")
        }
    }
}