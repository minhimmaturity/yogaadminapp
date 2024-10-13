package com.example.yoga_app.view

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yoga_app.component.Footer
import com.example.yoga_app.component.Header
import com.example.yoga_app.component.YogaCourseBody
import com.example.yoga_app.database.AppDatabase
import com.example.yoga_app.viewmodel.YogaClassCourseViewModel
import com.example.yoga_app.viewmodel.YogaClassAppViewModelFactory

@Composable
fun CenterAlignedTopAppBarExample(modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current
    val yogaCourseDao = AppDatabase.getDatabase(context).yogaCourseDao()
    val viewModel: YogaClassCourseViewModel = viewModel(
        factory = YogaClassAppViewModelFactory(yogaCourseDao)
    )
    val isCreatingClass = false


    Scaffold(
        modifier = modifier,
        topBar = { Header(navController, context) },
        bottomBar = { Footer(navController, isCreatingClass, "") }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            YogaCourseBody(viewModel, navController)
        }
    }
}
