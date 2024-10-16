package com.example.yoga_app.viewmodel


import com.example.yoga_app.dao.UserDao
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.yoga_app.dao.YogaClassDao
import com.example.yoga_app.dao.YogaCourseDao
import com.example.yoga_app.database.User
import com.example.yoga_app.database.YogaClass
import com.example.yoga_app.database.YogaCourse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao) : ViewModel() {
    val getAllInstructor: Flow<List<User>> = userDao.getAllInstructors()

    fun insertInstructor(user: User) {
        viewModelScope.launch {
            try {
                userDao.insertUser(user)
                Log.d("YogaClassAppViewModel", "Yoga class inserted successfully")
            } catch (e: Exception) {
                Log.e("YogaClassAppViewModel", "Error inserting yoga class", e)
                throw e
            }
        }
    }

    fun getInstructorById(instructorId: String): Flow<User?> {
        return userDao.getInstructorById(instructorId)
    }
}

class UserViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}