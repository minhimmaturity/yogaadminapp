package com.example.yoga_app.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun YogaCourseBody() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Yoga Courses", style = MaterialTheme.typography.headlineSmall)

        YogaCourseCard(
            dayOfWeek = "Monday",
            time = "10:00 AM",
            capacity = 10,
            duration = 60,
            price = "£10",
            typeOfClass = "Flow Yoga",
            description = "A dynamic flow of movements that synchronizes breath and motion."
        )

        YogaCourseCard(
            dayOfWeek = "Wednesday",
            time = "11:00 AM",
            capacity = 15,
            duration = 75,
            price = "£12",
            typeOfClass = "Aerial Yoga",
            description = "Yoga with the support of a silk hammock for a zero-gravity experience."
        )

        YogaCourseCard(
            dayOfWeek = "Friday",
            time = "9:00 AM",
            capacity = 12,
            duration = 45,
            price = "£8",
            typeOfClass = "Family Yoga",
            description = "A fun and interactive yoga session for the entire family."
        )
    }
}