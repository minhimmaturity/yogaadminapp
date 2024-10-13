package com.example.yoga_app.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val courseId: String,
    val day: String,
    val instructorName: String,
    val comment: String
)