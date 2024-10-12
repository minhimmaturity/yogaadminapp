package com.example.yoga_app.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yoga_app.component.CreateYogaCourse
import com.example.yoga_app.component.Footer
import com.example.yoga_app.component.Header
import com.example.yoga_app.database.AppDatabase
import com.example.yoga_app.viewmodel.YogaClassAppViewModel
import com.example.yoga_app.viewmodel.YogaClassAppViewModelFactory

@Composable
fun CreateScreen(modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current
    val yogaClassDao = AppDatabase.getDatabase(context).yogaClassDao()
    val viewModel: YogaClassAppViewModel = viewModel(
        factory = YogaClassAppViewModelFactory(yogaClassDao)
    )

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
