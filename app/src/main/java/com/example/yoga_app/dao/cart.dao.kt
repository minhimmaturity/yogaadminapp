package com.example.yoga_app.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.yoga_app.database.Cart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Transaction
    @Query("SELECT * FROM Carts")
    fun getCartWithYogaClasses(): Flow<List<Cart>>
}
