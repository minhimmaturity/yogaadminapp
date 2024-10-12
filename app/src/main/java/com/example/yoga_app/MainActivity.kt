package com.example.yoga_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.yoga_app.dao.YogaClassDao
import com.example.yoga_app.ui.theme.YogaappTheme
import com.example.yoga_app.database.AppDatabase
import com.example.yoga_app.viewmodel.YogaClassAppViewModel
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var db: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            Log.e("MainActivity", "Firebase init failed")
        }

        // Initialize the Room database
        try {
            db = AppDatabase.getDatabase(this)
            printAllTables()
            Log.d("MainActivity", "Database connected")
        } catch (e: Exception) {
            Log.e("MainActivity", "Failed to connect to the database", e)
        }

        try {
            enableEdgeToEdge()
            setContent {
                YogaappTheme {
                    Navigation(modifier = Modifier.fillMaxSize())
                }
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "${e.message}")
        }
    }

    private fun printAllTables() {
        CoroutineScope(Dispatchers.IO).launch {
            val cursor = db.openHelper.readableDatabase.query(
                "SELECT name FROM sqlite_master WHERE type = 'table'"
            )
            if (cursor.moveToFirst()) {
                do {
                    val tableName = cursor.getString(0)
                    Log.d("DatabaseTable", "Table: $tableName")
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
    }
}




