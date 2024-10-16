package com.example.yoga_app.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yoga_app.database.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): User?

    @Query("SELECT * FROM users WHERE role = 'INSTRUCTOR'")
    fun getAllInstructors(): Flow<List<User>>

    @Query("Select * from users where id = :instructorId")
    fun getInstructorById(instructorId: String): Flow<User?>
}