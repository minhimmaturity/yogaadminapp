package com.example.yoga_app.component

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
import com.example.yoga_app.viewmodel.YogaClassViewModel

@Composable
fun YogaClassBody(viewModel: YogaClassViewModel = viewModel(), courseId: String?) {
    val yogaClasses by viewModel.getYogaClassesByCourseId(courseId).collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Yoga Class", style = MaterialTheme.typography.headlineSmall)

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(yogaClasses) { yogaClass ->
                YogaClassCard(
                    date = yogaClass.day,
                    Teacher = yogaClass.instructorName,
                    comment = yogaClass.comment,
                    onEdit = {
                        // Handle edit action here
                    },
                    onDelete = {

                    },
                )
            }
        }
    }
}