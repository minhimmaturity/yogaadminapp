package com.example.yoga_app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.yoga_app.dao.YogaClassDao
import com.example.yoga_app.database.YogaClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class YogaClassAppViewModel(private val yogaClassDao: YogaClassDao) : ViewModel() {
    val allYogaClasses: Flow<List<YogaClass>> = yogaClassDao.getAllYogaClasses()

    fun insertYogaClass(yogaClass: YogaClass) {
        viewModelScope.launch {
            try {
                yogaClassDao.insertYogaClass(yogaClass)
                Log.d("YogaClassAppViewModel", "Yoga class inserted successfully")
            } catch (e: Exception) {
                Log.e("YogaClassAppViewModel", "Error inserting yoga class", e)
                throw e
            }
        }
    }
}

class YogaClassAppViewModelFactory(private val yogaClassDao: YogaClassDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YogaClassAppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return YogaClassAppViewModel(yogaClassDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}