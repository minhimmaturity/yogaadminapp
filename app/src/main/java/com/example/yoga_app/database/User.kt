package com.example.yoga_app.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.yoga_app.utils.UserRole
import java.util.UUID

@Entity(tableName = "users")
@TypeConverters(UserRoleConverter::class)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val email: String,
    val age: Int,
    val role: UserRole
)

class UserRoleConverter {
    @TypeConverter
    fun fromUserRole(role: UserRole): String {
        return role.name
    }

    @TypeConverter
    fun toUserRole(role: String): UserRole {
        return UserRole.valueOf(role)
    }
}
