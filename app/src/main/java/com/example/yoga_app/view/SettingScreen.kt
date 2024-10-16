package com.example.yoga_app.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yoga_app.component.CreateInstructor
import com.example.yoga_app.component.Footer
import com.example.yoga_app.component.Header
import com.example.yoga_app.database.AppDatabase
import com.example.yoga_app.viewmodel.UserViewModel
import com.example.yoga_app.viewmodel.UserViewModelFactory
import com.example.yoga_app.viewmodel.YogaClassAppViewModelFactory
import com.example.yoga_app.viewmodel.YogaClassCourseViewModel

@Composable
fun SettingScreen(modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current
    val isCreatingClass = false

    val userDao = AppDatabase.getDatabase(context).userDao()

    val viewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(userDao)
    )

    Scaffold(
        modifier = modifier,
        topBar = { Header(navController, context, title = "Setting") },
        bottomBar = { Footer(navController, isCreatingClass, "") }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            CreateInstructor()
        }
    }
}