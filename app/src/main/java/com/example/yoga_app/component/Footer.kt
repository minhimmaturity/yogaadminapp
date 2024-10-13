package com.example.yoga_app.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.yoga_app.Routes

@Composable
fun Footer(navController: NavController, isCreatingClass: Boolean, courseId: String?) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        content = {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { navController.navigate(Routes.Home.route) }) {
                        Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")
                    }
                    Text("Home", style = MaterialTheme.typography.bodySmall)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Update the action based on whether a class is being created
                    IconButton(onClick = {
                        if (isCreatingClass) {
                            navController.navigate("CreateClass/$courseId")
                        } else {
                            navController.navigate(Routes.Create.route)
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Create")
                    }
                    Text("Create", style = MaterialTheme.typography.bodySmall)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { navController.navigate(Routes.Setting.route) }) {
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
                    }
                    Text("Settings", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    )
}