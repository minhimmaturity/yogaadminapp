package com.example.yoga_app.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "yoga_class",
    foreignKeys = [ForeignKey(
        entity = YogaCourse::class,
        parentColumns = ["id"],
        childColumns = ["courseId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class YogaClass(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val courseId: String,
    val day: String,
    val instructorName: String,
    val comment: String
)