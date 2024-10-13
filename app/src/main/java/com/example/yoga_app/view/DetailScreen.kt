package com.example.yoga_app.view

import android.content.Context
import android.content.LocusId
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yoga_app.component.Footer
import com.example.yoga_app.component.Header
import com.example.yoga_app.component.YogaClassBody
import com.example.yoga_app.component.YogaCourseBody
import com.example.yoga_app.database.AppDatabase
import com.example.yoga_app.viewmodel.YogaClassAppViewModelFactory
import com.example.yoga_app.viewmodel.YogaClassCourseViewModel
import com.example.yoga_app.viewmodel.YogaClassViewModel
import com.example.yoga_app.viewmodel.YogaClassViewModelFactory


@Composable
fun DetailScreen(modifier: Modifier = Modifier, navController: NavController, courseId: String?) {
    val context = LocalContext.current
    val yogaClassDao = AppDatabase.getDatabase(context).yogaClassDao()
    val viewModel: YogaClassViewModel = viewModel(
        factory = YogaClassViewModelFactory(yogaClassDao)
    )
    Scaffold(
        modifier = modifier,
        topBar = { Header(navController, context) },
        bottomBar = { Footer(navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            YogaClassBody(viewModel, courseId)
        }
    }
}