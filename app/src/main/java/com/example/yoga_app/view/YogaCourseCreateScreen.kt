package com.example.yoga_app.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.yoga_app.component.CreateYogaCourse
import com.example.yoga_app.component.Footer
import com.example.yoga_app.component.Header

@Composable
fun CreateScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(
        modifier = modifier,
        topBar = { Header(navController) },
        bottomBar = { Footer(navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            CreateYogaCourse()
        }
    }
}