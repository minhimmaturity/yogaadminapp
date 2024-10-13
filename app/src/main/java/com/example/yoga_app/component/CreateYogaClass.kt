package com.example.yoga_app.component

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yoga_app.database.YogaCourse

@Composable
fun CreateYogaClass(courseId: String?) {
    val context = LocalContext.current

    var className by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = className,
            onValueChange = { newValue ->
                className = newValue
            },
            label = { Text(text = "Class Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
        )
        OutlinedTextField(
            value = className,
            onValueChange = { newValue ->
                className = newValue
            },
            label = { Text(text = "Class Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
        )
        OutlinedTextField(
            value = comment,
            onValueChange = { newValue ->
                comment = newValue
            },
            label = { Text(text = "Comment") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
        )
        // Save Button
        FilledTonalButton(
            onClick = {
//                if (isFormValid) {
//                    try {
//                        val newYogaClass = YogaCourse(
//                            className = className,
//                            yogaClassType = selectedYogaClass,
//                            dayOfWeek = selectedWeekDays.name,
//                            time = String.format("%02d:%02d", selectedHour, selectedMinute),
//                            capacity = capacity,
//                            duration = duration,
//                            pricePerClass = pricePerClass.toDoubleOrNull() ?: 0.0,
//                            description = description.takeIf { it.isNotEmpty() },
//                            location = location.takeIf { it.isNotEmpty() }
//                        )
//
//                        if (courseId != null) {
//                            if (courseId.isNotEmpty()) {
//                                val updateYogaCourse = YogaCourse(
//                                    id = (existingYogaCourse?.id ?: "").toString(),
//                                    className = className,
//                                    yogaClassType = selectedYogaClass,
//                                    dayOfWeek = selectedWeekDays.name,
//                                    time = String.format("%02d:%02d", selectedHour, selectedMinute),
//                                    capacity = capacity,
//                                    duration = duration,
//                                    pricePerClass = pricePerClass.toDoubleOrNull() ?: 0.0,
//                                    description = description.takeIf { it.isNotEmpty() },
//                                    location = location.takeIf { it.isNotEmpty() }
//                                )
//
//                                viewModel.updateCourse(updateYogaCourse)
//                                Toast.makeText(context, "Yoga class updated successfully", Toast.LENGTH_SHORT).show()
//                            } else {
//                                // Insert new course
//                                viewModel.insertYogaClass(newYogaClass)
//                                Toast.makeText(context, "Yoga class saved successfully", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    } catch (e: Exception) {
//                        Log.e("CreateYogaCourse", "Error saving yoga class", e)
//                        Toast.makeText(context, "Error saving yoga class: ${e.message}", Toast.LENGTH_LONG).show()
//                    }
//                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
//            enabled = isFormValid,
            colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.Gray)
        ) {
            Text(text = "Save", fontSize = 18.sp, modifier = Modifier.padding(4.dp))
        }
    }
}