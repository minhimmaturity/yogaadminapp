package com.example.yoga_app

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.yoga_app.ui.theme.YogaappTheme
import com.example.yoga_app.database.AppDatabase
import com.example.yoga_app.database.User
import com.example.yoga_app.database.YogaClass
import com.example.yoga_app.database.YogaCourse
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

fun uploadDataToFirebase(context: Context) {
    val db = AppDatabase.getDatabase(context)
    val database = Firebase.database("https://yogaadminapp-61adb-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val myRef = database.getReference("yogaadminapp")

    // Coroutine to handle data upload
    CoroutineScope(Dispatchers.IO).launch {
        try {
            // Collect data from Room
            val yogaCourses = db.yogaCourseDao().getAllYogaCourses().first()
            val yogaClasses = db.yogaClassDao().getAllYogaClasses().first()
            val users = db.userDao().getAllUser().first()

            // Convert each entity to a map and organize the data
            val dataMap = hashMapOf<String, Any>(
                "yogaCourses" to yogaCourses.map { it.toMap() },
                "yogaClasses" to yogaClasses.map { it.toMap() },
                "users" to users.map { it.toMap() }
            )

            // Upload to Firebase
            myRef.setValue(dataMap)
                .addOnSuccessListener {
                    Log.d("MainActivity", "Data uploaded successfully to Firebase.")
                }
                .addOnFailureListener { e ->
                    Log.e("MainActivity", "Failed to upload data to Firebase.", e)
                }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error fetching or uploading data", e)
        }
    }
}

class MainActivity : ComponentActivity() {
    private lateinit var db: AppDatabase
    private lateinit var myRef: DatabaseReference

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

            val database = Firebase.database("https://yogaadminapp-61adb-default-rtdb.asia-southeast1.firebasedatabase.app/")
            myRef = database.getReference("yogaadminapp")

            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Read data as a Map or your custom class type
                    val value = dataSnapshot.getValue(object : GenericTypeIndicator<HashMap<String, Any>>() {})
                    if (value != null) {
                        Log.d(TAG, "Value is: $value")
                    } else {
                        Log.d(TAG, "No data found")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException())
                }
            })

            uploadDataToFirebase(this)


            printAllTables()
            Log.d("MainActivity", "Database connected")
        } catch (e: Exception) {
            Log.e("MainActivity", "Failed to connect to the database", e)
        }

        try {
            enableEdgeToEdge()
            setContent {
                YogaappTheme {
                    Navigation(modifier = Modifier.fillMaxSize(), this)
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

fun YogaCourse.toMap(): Map<String, Any?> {
    return mapOf(
        "id" to id,
        "className" to className,
        "yogaClassType" to yogaClassType.name, // Convert enum to string
        "dayOfWeek" to dayOfWeek,
        "time" to time,
        "capacity" to capacity,
        "duration" to duration,
        "pricePerClass" to pricePerClass,
        "description" to description,
        "location" to location,
        "createdAt" to createdAt,
        "updatedAt" to updatedAt,
        "updatedBy" to updatedBy
    )
}

fun YogaClass.toMap(): Map<String, Any?> {
    return mapOf(
        "id" to id,
        "courseId" to courseId,
        "day" to day,
        "instructorId" to instructorId,
        "comment" to comment
    )
}

fun User.toMap(): Map<String, Any?> {
    return mapOf(
        "id" to id,
        "name" to name,
        "email" to email,
        "password" to password, // Consider hashing password before upload
        "role" to role.name // Convert enum to string
    )
}

