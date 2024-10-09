package com.example.yoga_app.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yoga_app.database.YogaClass
import kotlinx.coroutines.flow.Flow

@Dao
interface YogaClassDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYogaClass(yogaClass: YogaClass)

    @Query("SELECT * FROM yogaclass WHERE id = :classId")
    suspend fun getYogaClassById(classId: String): YogaClass?

    @Query("SELECT * FROM yogaclass")
    fun getAllYogaClasses(): Flow<List<YogaClass>>
}