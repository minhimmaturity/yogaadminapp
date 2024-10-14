package com.example.yoga_app.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.yoga_app.database.YogaClass
import kotlinx.coroutines.flow.Flow

@Dao
interface YogaClassDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYogaClass(yogaClass: YogaClass)

    @Query("SELECT * FROM yoga_class")
    fun getAllYogaClasses(): Flow<List<YogaClass>>

    @Query("SELECT * FROM yoga_class WHERE courseId = :courseId")
    fun getYogaClassesByCourseId(courseId: String?): Flow<List<YogaClass>>

    @Query("DELETE FROM yoga_class WHERE id = :id")
    suspend fun deleteYogaClass(id: String)

    @Query("SELECT * FROM yoga_class WHERE id = :id")
    fun getYogaClassById(id: String): Flow<YogaClass?>

    @Update()
    suspend fun updateYogaClass(yogaClass: YogaClass)
}
