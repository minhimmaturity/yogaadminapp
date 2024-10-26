package com.example.yoga_app

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.yoga_app.ui.theme.YogaappTheme
import com.example.yoga_app.database.AppDatabase
import com.example.yoga_app.database.User
import com.example.yoga_app.database.YogaClass
import com.example.yoga_app.database.YogaCourse
import com.example.yoga_app.utils.UserRole
import com.example.yoga_app.utils.YogaClassType
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
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

            // uploadDataToFirebase(this)


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

    private fun insertDataIntoLocalDatabase(data: HashMap<String, Any>) {
        // Extract YogaCourse, YogaClass, and User data from the HashMap
        val yogaCoursesData = data["yogaCourses"] as? List<Map<String, Any?>>
        val yogaClassesData = data["yogaClasses"] as? List<Map<String, Any?>>
        val usersData = data["users"] as? List<Map<String, Any?>>

        CoroutineScope(Dispatchers.IO).launch {
            // Insert YogaCourses into Room database
            yogaCoursesData?.forEach {
                val yogaCourse = it.toYogaCourse()
                db.yogaCourseDao().insertYogaCourse(yogaCourse)
            }

            // Insert YogaClasses into Room database
            yogaClassesData?.forEach {
                val yogaClass = it.toYogaClass()
                db.yogaClassDao().insertYogaClass(yogaClass)
            }

            // Insert Users into Room database
            usersData?.forEach {
                val user = it.toUser()
                db.userDao().insertUser(user)
            }

            Log.d("MainActivity", "Data successfully inserted into SQLite.")
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

fun Map<String, Any?>.toYogaCourse(): YogaCourse {
    return YogaCourse(
        id = this["id"] as String,
        className = this["className"] as String,
        yogaClassType = YogaClassType.valueOf(this["yogaClassType"] as String),
        dayOfWeek = this["dayOfWeek"] as String,
        time = this["time"] as String,
        capacity = this["capacity"] as Int,
        duration = this["duration"] as Int,
        pricePerClass = this["pricePerClass"] as Double,
        description = this["description"] as String?,
        location = this["location"] as String,
        createdAt = this["createdAt"] as Long,
        updatedAt = this["updatedAt"] as Long?,
        updatedBy = this["updatedBy"] as String?
    )
}

fun Map<String, Any?>.toYogaClass(): YogaClass {
    return YogaClass(
        id = this["id"] as String,
        courseId = this["courseId"] as String,
        day = this["day"] as String,
        instructorId = this["instructorId"] as String,
        comment = this["comment"] as String
    )
}

fun Map<String, Any?>.toUser(): User {
    return User(
        id = this["id"] as Long,
        name = this["name"] as String,
        email = this["email"] as String,
        password = this["password"] as String,
        role = UserRole.valueOf(this["role"] as String)
    )
}


