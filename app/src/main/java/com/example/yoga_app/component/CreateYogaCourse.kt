package com.example.yoga_app.component

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yoga_app.database.YogaClass
import com.example.yoga_app.utils.YogaClassType
import com.example.yoga_app.viewmodel.YogaClassAppViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CreateYogaCourse() {
    val viewModel: YogaClassAppViewModel = viewModel()

    var className by remember { mutableStateOf("") }
    var selectedYogaClass by remember { mutableStateOf(YogaClassType.FLOWYOGA) }
    var dayOfWeek by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf(0) }
    var duration by remember { mutableStateOf(0) }
    var pricePerClass by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var instructorName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var isError by remember { mutableStateOf(false) }
    var isFormValid by remember { mutableStateOf(false) }

    isFormValid = className.isNotEmpty() && dayOfWeek.isNotEmpty() && time.isNotEmpty()

    var expanded by remember { mutableStateOf(false) }
    val yogaClassTypes = YogaClassType.values().toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Class Name
        OutlinedTextField(
            value = className,
            onValueChange = { newValue ->
                className = newValue
                isError = newValue.isEmpty()
            },
            isError = isError,
            label = { Text(text = "Class Name") },
            modifier = Modifier.fillMaxWidth().padding(4.dp),
        )
        if (isError) {
            Text(text = "This field is required", color = Color.Red, modifier = Modifier.padding(top = 4.dp))
        }

        Box(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
            Text(
                text = selectedYogaClass.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .padding(16.dp)
                    .border(1.dp, Color.Gray)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                yogaClassTypes.forEach { yogaClassType ->
                    DropdownMenuItem(
                        onClick = {
                            selectedYogaClass = yogaClassType
                            expanded = false
                        }, text = {yogaClassType.name}
                    )
                }
            }
        }

        // Day of the Week
        OutlinedTextField(
            value = dayOfWeek,
            onValueChange = { dayOfWeek = it },
            label = { Text(text = "Day of the Week") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )

        // Time
        OutlinedTextField(
            value = time,
            onValueChange = { time = it },
            label = { Text(text = "Time") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )

        // Capacity
        OutlinedTextField(
            value = capacity.toString(),
            onValueChange = { newValue -> capacity = newValue.toIntOrNull() ?: 0 },
            label = { Text(text = "Capacity") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )

        // Duration
        OutlinedTextField(
            value = duration.toString(),
            onValueChange = { newValue -> duration = newValue.toIntOrNull() ?: 0 },
            label = { Text(text = "Duration (minutes)") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )

        // Price Per Class
        OutlinedTextField(
            value = pricePerClass,
            onValueChange = { pricePerClass = it },
            label = { Text(text = "Price per Class") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )

        // Description
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(text = "Description (Optional)") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )

        // Instructor Name
        OutlinedTextField(
            value = instructorName,
            onValueChange = { instructorName = it },
            label = { Text(text = "Instructor Name") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )

        // Location
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text(text = "Location") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )

        // Submit Button
        FilledTonalButton(
            onClick = {
                if (isFormValid) {
                    val newYogaClass = YogaClass(
                        className = className,
                        yogaClassType = selectedYogaClass,
                        dayOfWeek = dayOfWeek,
                        time = time,
                        capacity = capacity,
                        duration = duration,
                        pricePerClass = pricePerClass.toDoubleOrNull() ?: 0.0,
                        description = description.takeIf { it.isNotEmpty() },
                        instructorName = instructorName.takeIf { it.isNotEmpty() },
                        location = location.takeIf { it.isNotEmpty() }
                    )

                    viewModel.insertYogaClass(newYogaClass)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = isFormValid,
            colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.Gray)
        ) {
            Text(text = "Save", fontSize = 18.sp, modifier = Modifier.padding(4.dp))
        }
    }
}
