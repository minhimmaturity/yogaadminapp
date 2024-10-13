package com.example.yoga_app.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun YogaCourseCard(
    courseName: String,
    dayOfWeek: String,
    time: String,
    capacity: Int,
    duration: Int,
    price: String,
    typeOfClass: String,
    description: String?,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onViewDetail: () -> Unit
) {
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
            Text(text = "Course Name: $courseName", style = MaterialTheme.typography.titleMedium)
            Text(text = "Day: $dayOfWeek", style = MaterialTheme.typography.titleMedium)
            Text(text = "Time: $time", style = MaterialTheme.typography.titleMedium)
            Text(text = "Capacity: $capacity persons", style = MaterialTheme.typography.titleMedium)
            Text(text = "Duration: $duration minutes", style = MaterialTheme.typography.titleMedium)
            Text(text = "Price: $price", style = MaterialTheme.typography.titleMedium)
            Text(text = "Type: $typeOfClass", style = MaterialTheme.typography.titleMedium)

            if (!description.isNullOrEmpty()) {
                Text(text = "Description: $description", style = MaterialTheme.typography.bodyMedium)
            }

            // Optional: Add a divider for better separation
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onViewDetail) {
                    Icon(Icons.Default.Visibility, contentDescription = "View Details", tint = MaterialTheme.colorScheme.error)
                }
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
