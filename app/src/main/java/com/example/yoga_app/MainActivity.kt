package com.example.yoga_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.yoga_app.database.DatabaseHelper
import com.example.yoga_app.ui.theme.YogaappTheme

class MainActivity : ComponentActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        dbHelper = DatabaseHelper(this)

        try {
            this.dbHelper.connectDatabase();
            Log.d("MainActivity", "Database connected successfully.")
        } catch(e: Exception) {
            Log.e("MainActivity", "Failed to connect to the database: ${e.message}")
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YogaappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YogaappTheme {
        Greeting("Android")
    }
}