package com.example.yoga_app.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun YogaCourseCard(
    dayOfWeek: String,
    time: String,
    capacity: Int,
    duration: Int,
    price: String,
    typeOfClass: String,
    description: String?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Day: $dayOfWeek", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Time: $time", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Capacity: $capacity persons", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Duration: $duration minutes", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Price: $price", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Type: $typeOfClass", style = MaterialTheme.typography.bodyLarge)

            if (!description.isNullOrEmpty()) {
                Text(text = "Description: $description", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}