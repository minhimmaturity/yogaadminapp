package com.example.yoga_app.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.yoga_app.utils.YogaClassType
import java.util.UUID

@Entity(tableName = "yoga_course")
@TypeConverters(YogaClassTypeConverter::class)
data class YogaCourse(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val className: String,
    val yogaClassType: YogaClassType,
    val dayOfWeek: String,
    val time: String,
    val capacity: Int,
    val duration: Int,
    val pricePerClass: Double,
    val description: String?,
    val location: String?,
    val createdAt: Long = System.currentTimeMillis(),
    var updatedAt: Long? = System.currentTimeMillis(),
    var updatedBy: String? = null
)

class YogaClassTypeConverter {
    @TypeConverter
    fun fromYogaClassType(yogaClassType: YogaClassType): String {
        return yogaClassType.name
    }

    @TypeConverter
    fun toYogaClassType(yogaClassType: String): YogaClassType {
        return YogaClassType.valueOf(yogaClassType)
    }
}


