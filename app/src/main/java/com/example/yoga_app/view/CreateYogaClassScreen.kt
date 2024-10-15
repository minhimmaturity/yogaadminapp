package com.example.yoga_app.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yoga_app.component.CreateYogaClass
import com.example.yoga_app.component.Footer
import com.example.yoga_app.component.Header
import com.example.yoga_app.database.AppDatabase
import com.example.yoga_app.viewmodel.YogaClassCourseViewModel
import com.example.yoga_app.viewmodel.YogaClassAppViewModelFactory
import com.example.yoga_app.viewmodel.YogaClassViewModel
import com.example.yoga_app.viewmodel.YogaClassViewModelFactory

@Composable
fun CreateYogaClassScreen(modifier: Modifier = Modifier, navController: NavController, courseId: String?, classId: String?) {
    val context = LocalContext.current
    val yogaCourseDao = AppDatabase.getDatabase(context).yogaCourseDao()
    val yogaClassDao = AppDatabase.getDatabase(context).yogaClassDao()
    val viewModel: YogaClassCourseViewModel = viewModel(
        factory = YogaClassAppViewModelFactory(yogaCourseDao)
    )

    val classViewModel: YogaClassViewModel = viewModel(
        factory = YogaClassViewModelFactory(yogaClassDao)
    )

    val isCreatingClass = true

    Scaffold(
        modifier = modifier,
        topBar = { Header(navController, context, title = "Yoga Class") },
        bottomBar = { Footer(navController, isCreatingClass, courseId) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            CreateYogaClass(courseId, classId)
        }
    }
}
