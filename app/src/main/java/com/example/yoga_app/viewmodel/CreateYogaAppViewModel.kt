package com.example.yoga_app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.yoga_app.dao.YogaCourseDao
import com.example.yoga_app.database.YogaCourse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class YogaClassCourseViewModel(private val yogaCourseDao: YogaCourseDao) : ViewModel() {
    val getAllYogaCourse: Flow<List<YogaCourse>> = yogaCourseDao.getAllYogaCourses()

    fun insertYogaClass(yogaClass: YogaCourse) {
        viewModelScope.launch {
            try {
                yogaCourseDao.insertYogaCourse(yogaClass)
                Log.d("YogaClassAppViewModel", "Yoga class inserted successfully")
            } catch (e: Exception) {
                Log.e("YogaClassAppViewModel", "Error inserting yoga class", e)
                throw e
            }
        }
    }

    fun getYogaCourseById(id: String?): Flow<YogaCourse?> {
        return if (id != null) {
            yogaCourseDao.getYogaClassById(id)
        } else {
            kotlinx.coroutines.flow.flowOf(null)
        }
    }

    fun deleteCourse(courseId: String) {
        viewModelScope.launch {
            yogaCourseDao.deleteCourse(courseId)
        }
    }

    fun updateCourse(course: YogaCourse) {
        viewModelScope.launch {
            yogaCourseDao.updateYogaCourse(course);
        }
    }
}

class YogaClassAppViewModelFactory(private val yogaCourseDao: YogaCourseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YogaClassCourseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return YogaClassCourseViewModel(yogaCourseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
