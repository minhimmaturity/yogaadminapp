package com.example.yoga_app.component

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yoga_app.database.YogaClass
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

    var isFormValid by remember { mutableStateOf(false) }

    var instructorName by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    var selectedDate by remember { mutableStateOf("") }

    var selectedDayOfWeek by remember { mutableStateOf("") }

    val courseDayOfWeek = existingYogaCourse?.dayOfWeek ?: ""

    isFormValid = instructorName.isNotEmpty() && selectedDate.isNotEmpty()

    LaunchedEffect(existingYogaClass) {
        existingYogaClass?.let { existingYogaClass ->
            selectedDate = existingYogaClass.day
            instructorName = existingYogaClass.instructorName
            comment = existingYogaClass.comment

            selectedDayOfWeek = getDayOfWeekFromDate(existingYogaClass.day)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { }, // Prevent manual editing
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
                        showDatePicker = false }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState, showModeToggle = false)
            }
        }

        Log.d("selectedDayOfWeek", "${selectedDayOfWeek}")
        Log.d("courseDayOfWeek", "${courseDayOfWeek}")

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

        // Instructor Name input
        OutlinedTextField(
            value = instructorName,
            onValueChange = { newValue -> instructorName = newValue },
            label = { Text(text = "Instructor Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        // Comment input
        OutlinedTextField(
            value = comment,
            onValueChange = { newValue -> comment = newValue },
            label = { Text(text = "Comment") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        // Save button
        FilledTonalButton(
            onClick = {
                if (isFormValid) {
                    try {
                        val newYogaClass = YogaClass(
                            courseId = existingYogaCourse?.id ?: "",
                            day = selectedDate,
                            instructorName = instructorName,
                            comment = comment
                        )
                        if (classId != null) {
                            if (classId.isNotEmpty()) {
                                val updateYogaClass = YogaClass(
                                    id = (existingYogaClass?.id ?: "").toString(),
                                    courseId = (existingYogaCourse?.id ?: "").toString(),
                                    day = selectedDate,
                                    instructorName = instructorName,
                                    comment = comment
                                )

                                classViewModel.updateYogaClass(updateYogaClass)
                                Toast.makeText(context, "Yoga class updated successfully", Toast.LENGTH_SHORT).show()
                            } else {
                                classViewModel.insertYogaClass(newYogaClass)
                                Toast.makeText(context, "Yoga class saved successfully", Toast.LENGTH_SHORT).show()
                            }
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
            Text(text = "Save", fontSize = 18.sp, modifier = Modifier.padding(4.dp))
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
        // Convert to the string representation of the day
        Log.d("Dayofweek", "${dayOfWeek}")

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