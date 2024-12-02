package com.example.yoga_app.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.UUID

@Entity(tableName = "Carts")
data class Cart(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val yogaClasses: List<YogaClass>
)
