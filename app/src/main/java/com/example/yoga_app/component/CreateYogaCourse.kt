package com.example.yoga_app.component

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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yoga_app.Routes

@Composable
fun CreateYogaCourse() {
    var courseName by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var isFormValid by remember { mutableStateOf(false) }
    isFormValid = courseName.isNotEmpty();

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = courseName,
            onValueChange = { newValue ->
                if (newValue.isNotEmpty()) {
                    courseName = newValue
                    isError = false
                } else {
                    isError = true
                }
            },
            isError = isError,
            label = { Text(text = "Course Name ") },
            modifier = Modifier.fillMaxWidth().padding(4.dp),
        )

        if (isError) {
            Text(
                text = "This field is required",
                color = Color.Red,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        TextField(
            value = courseName,
            onValueChange = { newValue ->
                if (newValue.length != 0) {
                    courseName = newValue
                }
            },
            label = { Text(text = "Course Name ") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)

        )
        TextField(
            value = courseName,
            onValueChange = { newValue ->
                if (newValue.length != 0) {
                    courseName = newValue
                }
            },
            label = { Text(text = "Course Name ") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)

        )
        TextField(
            value = courseName,
            onValueChange = { newValue ->
                if (newValue.length != 0) {
                    courseName = newValue
                }
            },
            label = { Text(text = "Course Name ") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)

        )
        TextField(
            value = courseName,
            onValueChange = { newValue ->
                if (newValue.length != 0) {
                    courseName = newValue
                }
            },
            label = { Text(text = "Course Name ") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)

        )
        TextField(
            value = courseName,
            onValueChange = { newValue ->
                if (newValue.length != 0) {
                    courseName = newValue
                }
            },
            label = { Text(text = "Course Name ") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)

        )
        FilledTonalButton(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = isFormValid,
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(
                text = "Save",
                fontSize = 18.sp,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}
