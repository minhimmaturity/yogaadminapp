package com.example.yoga_app.component

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yoga_app.Routes
import com.example.yoga_app.database.User
import com.example.yoga_app.uploadDataToFirebase
import com.example.yoga_app.utils.UserRole
import com.example.yoga_app.viewmodel.UserViewModel

@Composable
fun CreateInstructor() {
    val context = LocalContext.current

    val userViewModel: UserViewModel = viewModel()

    val allInstructor by userViewModel.getAllInstructor.collectAsState(initial = emptyList())

    var isFormValid by remember { mutableStateOf(false) }

    var instructorName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Enhanced validation
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})$"
    val isEmailValid = email.matches(emailRegex.toRegex())
    val isPasswordValid = password.length >= 6
    isFormValid = instructorName.isNotEmpty() && isEmailValid && isPasswordValid

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Form Header
        item {
            Text(
                text = "Create Instructor",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Instructor Name input
        item {
            OutlinedTextField(
                value = instructorName,
                onValueChange = { newValue -> instructorName = newValue },
                label = { Text(text = "Instructor Name") },
                isError = instructorName.isEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            if (instructorName.isEmpty()) {
                Text(
                    text = "Name cannot be empty",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }

        // Email input
        item {
            OutlinedTextField(
                value = email,
                onValueChange = { newValue -> email = newValue },
                label = { Text(text = "Email") },
                isError = email.isNotEmpty() && !isEmailValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )
            if (email.isNotEmpty() && !isEmailValid) {
                Text(
                    text = "Invalid email format",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }

        // Password input
        item {
            OutlinedTextField(
                value = password,
                onValueChange = { newValue -> password = newValue },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Enter your password") },
                isError = password.isNotEmpty() && !isPasswordValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )
            if (password.isNotEmpty() && !isPasswordValid) {
                Text(
                    text = "Password must be at least 6 characters",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }

        // Save button
        item {
            FilledTonalButton(
                onClick = {
                    if (isFormValid) {
                        try {
                            val user = User(
                                name = instructorName,
                                email = email,
                                password = password,
                                role = UserRole.INSTRUCTOR
                            )

                            // Assuming insertInstructor is a suspend function
                            // Launch a coroutine to insert the instructor
                            userViewModel.insertInstructor(user)

                            Log.d("CreateInstructor", "Instructor added successfully: $user")
                            Toast.makeText(context, "Add new instructor successfully", Toast.LENGTH_SHORT).show()

                            // Clear form fields after successful addition
                            instructorName = ""
                            email = ""
                            password = ""

                            uploadDataToFirebase(context)
                        } catch (e: Exception) {
                            Log.e("CreateInstructor", "Error saving instructor", e)
                            Toast.makeText(context, "Error saving instructor: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(context, "Please fill out all fields correctly", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isFormValid) Color.Blue else Color.Gray
                )
            ) {
                Text(text = "Save", fontSize = 18.sp, color = Color.White)
            }
        }

        // Divider or spacing between form and list
        item {
            Divider(color = Color.LightGray, thickness = 1.dp)
        }

        // List of Instructors
        items(allInstructor) { instructor ->
            InstructorCard(
                name = instructor.name,
                email = instructor.email
            )
        }
    }
}
