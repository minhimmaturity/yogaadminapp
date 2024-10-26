package com.example.yoga_app.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yoga_app.database.User
import com.example.yoga_app.viewmodel.UserViewModel

@Composable
fun YogaClassCard(
    date: String,
    teacherId : String,
    comment: String?,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {

    val instructorViewModel: UserViewModel = viewModel()
//    val existingInstructor by instructorViewModel.getInstructorById(Teacher)

    var instructor by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(teacherId) {
        instructorViewModel.getInstructorById(teacherId).collect { fetchedInstructor ->
            instructor = fetchedInstructor
        }
    }

    val instructorName = instructor?.name ?: ""
        Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Date: $date", style = MaterialTheme.typography.titleMedium)
            Text(text = "Teacher: $instructorName", style = MaterialTheme.typography.titleMedium)
            Text(text = "Comment: $comment", style = MaterialTheme.typography.titleMedium)

            // Optional: Add a divider for better separation
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Course", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Course", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
