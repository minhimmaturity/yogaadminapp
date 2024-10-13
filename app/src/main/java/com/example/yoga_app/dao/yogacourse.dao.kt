package com.example.yoga_app.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.yoga_app.database.YogaCourse
import kotlinx.coroutines.flow.Flow

@Dao
interface YogaCourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYogaCourse(yogaClass: YogaCourse)

    @Query("SELECT * FROM yoga_course WHERE id = :courseId")
    fun getYogaClassById(courseId: String?): Flow<YogaCourse?>

    @Query("SELECT * FROM yoga_course ORDER BY createdAt DESC")
    fun getAllYogaCourses(): Flow<List<YogaCourse>>

    @Query("DELETE FROM yoga_course WHERE id = :courseId")
    suspend fun deleteCourse(courseId: String)

    @Update
    suspend fun updateYogaCourse(yogaClass: YogaCourse)
}
