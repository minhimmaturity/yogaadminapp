package com.example.yoga_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yoga_app.dao.YogaClassDao
import com.example.yoga_app.database.YogaClass
import kotlinx.coroutines.launch

class YogaClassAppViewModel(private val yogaClassDao: YogaClassDao) : ViewModel() {
    fun insertYogaClass(yogaClass: YogaClass) {
        viewModelScope.launch {
            yogaClassDao.insertYogaClass(yogaClass)
        }
    }
}