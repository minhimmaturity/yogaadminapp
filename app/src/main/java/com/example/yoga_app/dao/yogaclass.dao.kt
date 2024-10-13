package com.example.yoga_app.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yoga_app.database.YogaClass
import com.example.yoga_app.database.YogaCourse
import kotlinx.coroutines.flow.Flow

@Dao
interface YogaClassDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYogaClass(yogaClass: YogaClass)

    @Query("SELECT * FROM yoga_class")
    fun getAllYogaClasses(): Flow<List<YogaClass>>

    @Query("SELECT * FROM yoga_class WHERE courseId = :courseId")
    fun getYogaClassesByCourseId(courseId: String?): Flow<List<YogaClass>>
}