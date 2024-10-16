package com.example.yoga_app.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yoga_app.Routes
import com.example.yoga_app.viewmodel.YogaClassViewModel

@Composable
fun YogaClassBody(
    viewModel: YogaClassViewModel = viewModel(),
    navController: NavController,
    courseId: String?
) {
    val yogaClasses by viewModel.getYogaClassesByCourseId(courseId)
        .collectAsState(initial = emptyList())
    val yogaClassViewModel: YogaClassViewModel = viewModel()

    var isSearchVisible by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Yoga Class", style = MaterialTheme.typography.headlineSmall)

            // IconButton to show search field
            IconButton(onClick = { isSearchVisible = !isSearchVisible }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search"
                )
            }
        }

        if (isSearchVisible) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search by Teacher's Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }

//        // Filter yoga classes based on search query
//        val filteredYogaClasses = if (searchQuery.isNotEmpty()) {
//            yogaClasses.filter {
//                it.instructor.contains(searchQuery, ignoreCase = true) ||
//                        it.day.contains(searchQuery, ignoreCase = true)  // Assuming `day` is a string representing the date
//            }
//        } else {
//            yogaClasses
//        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(yogaClasses) { yogaClass ->
                YogaClassCard(
                    date = yogaClass.day,
                    teacherId = yogaClass.instructorId,
                    comment = yogaClass.comment,
                    onEdit = {
                        navController.navigate(
                            Routes.EditClass.route
                                .replace("{courseId}", courseId.toString())
                                .replace("{classId}", yogaClass.id)
                        )
                    },
                    onDelete = {
                        yogaClassViewModel.deleteYogaClass(yogaClass.id)
                    },
                )
            }
        }
    }
}
