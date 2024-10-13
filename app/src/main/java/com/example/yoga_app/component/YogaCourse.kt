package com.example.yoga_app.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yoga_app.Routes
import com.example.yoga_app.viewmodel.YogaClassCourseViewModel

@Composable
fun YogaCourseBody(viewModel: YogaClassCourseViewModel = viewModel(), navController: NavController) {
    val yogaCourse by viewModel.getAllYogaCourse.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Yoga Courses", style = MaterialTheme.typography.headlineSmall)

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(yogaCourse) { yogaCourse ->
                YogaCourseCard(
                    courseName = yogaCourse.className,
                    dayOfWeek = yogaCourse.dayOfWeek,
                    time = "${yogaCourse.time}h",
                    capacity = yogaCourse.capacity,
                    duration = yogaCourse.duration,
                    price = "£${yogaCourse.pricePerClass}",
                    typeOfClass = yogaCourse.yogaClassType.name,
                    description = yogaCourse.description,
                    onEdit = {
                        navController.navigate(Routes.Edit.route.replace("{courseId}", yogaCourse.id))
                    },
                    onDelete = {
                        viewModel.deleteCourse(yogaCourse.id)
                    },
                    onViewDetail = {
                        navController.navigate("Detail/${yogaCourse.id}")
                    }
                )
            }
        }
    }
}
