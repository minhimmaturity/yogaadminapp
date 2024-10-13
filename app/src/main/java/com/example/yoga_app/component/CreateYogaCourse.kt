package com.example.yoga_app.component

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yoga_app.database.YogaCourse
import com.example.yoga_app.utils.Weekday
import com.example.yoga_app.utils.YogaClassType
import com.example.yoga_app.viewmodel.YogaClassCourseViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.collectAsState

@Composable
fun CreateYogaCourse(courseId: String?) {
    val viewModel: YogaClassCourseViewModel = viewModel()

    val existingYogaCourse by viewModel.getYogaCourseById(courseId).collectAsState(initial = null)

    val context = LocalContext.current

    var className by remember { mutableStateOf("") }
    var capacity by remember { mutableIntStateOf(0) }
    var duration by remember { mutableIntStateOf(0) }
    var pricePerClass by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var isError by remember { mutableStateOf(false) }
    var isFormValid by remember { mutableStateOf(false) }

    var yogaClassExpanded by remember { mutableStateOf(false) }
    var selectedYogaClass by remember { mutableStateOf(YogaClassType.FLOWYOGA) }
    val yogaClassTypes = YogaClassType.entries.toTypedArray()

    var weekDayExpanded by remember { mutableStateOf(false) }
    var selectedWeekDays by remember { mutableStateOf(Weekday.MONDAY) }
    val weekDays = Weekday.entries.toTypedArray()

    var selectedHour by remember { mutableIntStateOf(0) }
    var selectedMinute by remember { mutableIntStateOf(0) }
    var isTimePickerVisible by remember { mutableStateOf(false) }

    isFormValid = className.isNotEmpty()

    // Populate existing course data if editing
    LaunchedEffect(existingYogaCourse) {
        existingYogaCourse?.let { course ->
            className = course.className
            capacity = course.capacity
            duration = course.duration
            pricePerClass = course.pricePerClass.toString()
            description = course.description ?: ""
            location = course.location ?: ""
            selectedYogaClass = course.yogaClassType
            selectedWeekDays = Weekday.valueOf(course.dayOfWeek)
            val (hour, minute) = course.time.split(":").map { it.toInt() }
            selectedHour = hour
            selectedMinute = minute
        }
    }

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
                isError = newValue.isEmpty()
            },
            isError = isError,
            label = { Text(text = "Class Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
        )
        if (isError) {
            Text(
                text = "This field is required",
                color = Color.Red,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // Yoga Class Type Dropdown
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .background(MaterialTheme.colorScheme.surface)
                .clickable { yogaClassExpanded = true }
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(
                text = selectedYogaClass.name,
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenu(
                expanded = yogaClassExpanded,
                onDismissRequest = { yogaClassExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                yogaClassTypes.forEach { yogaClassType ->
                    DropdownMenuItem(
                        onClick = {
                            selectedYogaClass = yogaClassType
                            yogaClassExpanded = false
                        },
                        text = { Text(text = yogaClassType.name) }
                    )
                }
            }
        }

        // Weekday Dropdown
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .background(MaterialTheme.colorScheme.surface)
                .clickable { weekDayExpanded = true }
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(
                text = selectedWeekDays.name,
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenu(
                expanded = weekDayExpanded,
                onDismissRequest = { weekDayExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                weekDays.forEach { weekday ->
                    DropdownMenuItem(
                        onClick = {
                            selectedWeekDays = weekday
                            weekDayExpanded = false
                        },
                        text = { Text(text = weekday.name) }
                    )
                }
            }
        }

        // Selected Time
        Text(
            text = "Selected Time: %02d:%02d".format(selectedHour, selectedMinute),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = { isTimePickerVisible = true }) {
            Text(text = "Pick a Time")
        }

        if (isTimePickerVisible) {
            TimePickerDialog(
                initialHour = selectedHour,
                initialMinute = selectedMinute,
                onTimeChange = { newHour, newMinute ->
                    selectedHour = newHour
                    selectedMinute = newMinute
                    isTimePickerVisible = false
                },
                onDismiss = { isTimePickerVisible = false }
            )
        }

        // Capacity Field
        OutlinedTextField(
            value = capacity.toString(),
            onValueChange = { newValue -> capacity = newValue.toIntOrNull() ?: 0 },
            label = { Text(text = "Capacity") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        // Duration Field
        OutlinedTextField(
            value = duration.toString(),
            onValueChange = { newValue -> duration = newValue.toIntOrNull() ?: 0 },
            label = { Text(text = "Duration (minutes)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        // Price Per Class Field
        OutlinedTextField(
            value = pricePerClass,
            onValueChange = { pricePerClass = it },
            label = { Text(text = "Price per Class") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        // Description Field
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(text = "Description (Optional)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        // Location Field
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text(text = "Location") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        // Save Button
        FilledTonalButton(
            onClick = {
                if (isFormValid) {
                    try {
                        val newYogaClass = YogaCourse(
                            className = className,
                            yogaClassType = selectedYogaClass,
                            dayOfWeek = selectedWeekDays.name,
                            time = String.format("%02d:%02d", selectedHour, selectedMinute),
                            capacity = capacity,
                            duration = duration,
                            pricePerClass = pricePerClass.toDoubleOrNull() ?: 0.0,
                            description = description.takeIf { it.isNotEmpty() },
                            location = location.takeIf { it.isNotEmpty() }
                        )

                        if (courseId != null) {
                            if (courseId.isNotEmpty()) {
                                val updateYogaCourse = YogaCourse(
                                    id = (existingYogaCourse?.id ?: "").toString(),
                                    className = className,
                                    yogaClassType = selectedYogaClass,
                                    dayOfWeek = selectedWeekDays.name,
                                    time = String.format("%02d:%02d", selectedHour, selectedMinute),
                                    capacity = capacity,
                                    duration = duration,
                                    pricePerClass = pricePerClass.toDoubleOrNull() ?: 0.0,
                                    description = description.takeIf { it.isNotEmpty() },
                                    location = location.takeIf { it.isNotEmpty() }
                                )

                                viewModel.updateCourse(updateYogaCourse)
                                Toast.makeText(context, "Yoga class updated successfully", Toast.LENGTH_SHORT).show()
                            } else {
                                // Insert new course
                                viewModel.insertYogaClass(newYogaClass)
                                Toast.makeText(context, "Yoga class saved successfully", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("CreateYogaCourse", "Error saving yoga class", e)
                        Toast.makeText(context, "Error saving yoga class: ${e.message}", Toast.LENGTH_LONG).show()
                    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    initialHour: Int,  // Initial hour from existingYogaCourse
    initialMinute: Int,  // Initial minute from existingYogaCourse
    onTimeChange: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    // Use the initialHour and initialMinute to initialize the state
    val timePickerState = remember { TimePickerState(initialHour, initialMinute, is24Hour = true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Select Time") },
        confirmButton = {
            Button(onClick = {
                // Pass the selected time back to the parent composable
                onTimeChange(timePickerState.hour, timePickerState.minute)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        text = {
            TimePicker(
                state = timePickerState
            )
        }
    )
}
