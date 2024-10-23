package com.example.yoga_app.component

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yoga_app.database.YogaClass
import com.example.yoga_app.uploadDataToFirebase
import com.example.yoga_app.viewmodel.UserViewModel
import com.example.yoga_app.viewmodel.YogaClassCourseViewModel
import com.example.yoga_app.viewmodel.YogaClassViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateYogaClass(courseId: String?, classId: String?) {
    val context = LocalContext.current

    val yogaCourseViewModel: YogaClassCourseViewModel = viewModel()
    val existingYogaCourse by yogaCourseViewModel.getYogaCourseById(courseId).collectAsState(initial = null)

    val classViewModel: YogaClassViewModel = viewModel()
    val existingYogaClass by classViewModel.getYogaClassById(classId).collectAsState(initial = null)

    val instructorViewModel: UserViewModel = viewModel()
    val allInstructor by instructorViewModel.getAllInstructor.collectAsState(initial = emptyList())

    var isFormValid by remember { mutableStateOf(false) }

    var instructorId by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    var showInstructorDropdown by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf("") }

    var selectedDayOfWeek by remember { mutableStateOf("") }

    val courseDayOfWeek = existingYogaCourse?.dayOfWeek ?: ""

    isFormValid = instructorId.isNotEmpty() && selectedDate.isNotEmpty()

    LaunchedEffect(existingYogaClass) {
        existingYogaClass?.let { existingYogaClass ->
            selectedDate = existingYogaClass.day
            instructorId = existingYogaClass.instructorId
            comment = existingYogaClass.comment

            selectedDayOfWeek = getDayOfWeekFromDate(existingYogaClass.day)
        }
    }

    val calendar = Calendar.getInstance()
    val initialDate = if (selectedDate.isNotEmpty()) {
        try {
            SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).parse(selectedDate)?.let {
                calendar.time = it
                calendar.timeInMillis
            }
        } catch (e: Exception) {
            null
        }
    } else null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("Day of class") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },

                confirmButton = {
                    TextButton(onClick = {
                        selectedDate = convertMillisToDate(datePickerState.selectedDateMillis!!)
                        selectedDayOfWeek = getDayOfWeekFromDate(selectedDate)
                        showDatePicker = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = false,
                )

                LaunchedEffect(initialDate) {
                    initialDate?.let {
                        datePickerState.selectedDateMillis = it
                    }
                }
            }
        }

        if (courseDayOfWeek.isNotEmpty()) {
            Text(
                text = "Course day: $courseDayOfWeek",
                color = if (selectedDayOfWeek.equals(courseDayOfWeek, ignoreCase = true)) Color.Green else Color.Red,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            if (!selectedDayOfWeek.equals(courseDayOfWeek, ignoreCase = true) && selectedDate.isNotEmpty()) {
                Text(
                    text = "Selected day does not match the course day of week",
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }

        // Instructor Dropdown
        ExposedDropdownMenuBox(
            expanded = showInstructorDropdown,
            onExpandedChange = {
                showInstructorDropdown = !showInstructorDropdown
            }
        ) {
            OutlinedTextField(
                value = allInstructor.firstOrNull { it.id.toString() == instructorId }?.name ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Instructor") },
                trailingIcon = {
                    Icon(
                        imageVector = if (showInstructorDropdown) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = if (showInstructorDropdown) "Collapse dropdown" else "Expand dropdown"
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = showInstructorDropdown,
                onDismissRequest = { showInstructorDropdown = false }
            ) {
                allInstructor.forEach { instructor ->
                    DropdownMenuItem(
                        onClick = {
                            instructorId = instructor.id.toString()
                            showInstructorDropdown = false
                        },
                        text = { Text(text = instructor.name) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Comment input
        OutlinedTextField(
            value = comment,
            onValueChange = { newValue -> comment = newValue },
            label = { Text("Comment") },
            modifier = Modifier
                .fillMaxWidth()
        )

        // Save button
        FilledTonalButton(
            onClick = {
                if (isFormValid) {
                    try {
                        val newYogaClass = YogaClass(
                            courseId = existingYogaCourse?.id ?: "",
                            day = selectedDate,
                            instructorId = instructorId,
                            comment = comment
                        )
                        if (!classId.isNullOrEmpty()) {
                            val updateYogaClass = YogaClass(
                                id = (existingYogaClass?.id ?: "").toString(),
                                courseId = (existingYogaCourse?.id ?: "").toString(),
                                day = selectedDate,
                                instructorId = instructorId,
                                comment = comment
                            )

                            classViewModel.updateYogaClass(updateYogaClass)
                            Toast.makeText(context, "Yoga class updated successfully", Toast.LENGTH_SHORT).show()

                            uploadDataToFirebase(context)
                        } else {
                            classViewModel.insertYogaClass(newYogaClass)
                            Toast.makeText(context, "Yoga class saved successfully", Toast.LENGTH_SHORT).show()

                            uploadDataToFirebase(context)
                        }
                    } catch (e: Exception) {
                        Log.e("CreateYogaClass", "Error saving yoga class", e)
                        Toast.makeText(context, "Error saving yoga class: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.Gray)
        ) {
            Text("Save", fontSize = 18.sp, modifier = Modifier.padding(4.dp))
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun getDayOfWeekFromDate(date: String): String {
    return try {
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val dateObj = formatter.parse(date)
        val calendar = Calendar.getInstance().apply { time = dateObj!! }
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        when (dayOfWeek) {
            Calendar.SUNDAY -> "SUNDAY"
            Calendar.MONDAY -> "MONDAY"
            Calendar.TUESDAY -> "TUESDAY"
            Calendar.WEDNESDAY -> "WEDNESDAY"
            Calendar.THURSDAY -> "THURSDAY"
            Calendar.FRIDAY -> "FRIDAY"
            Calendar.SATURDAY -> "SATURDAY"
            else -> ""
        }

    } catch (e: Exception) {
        ""
    }
}