package com.example.yoga_app.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import com.example.yoga_app.dao.UserDao
import com.example.yoga_app.dao.YogaClassDao
import com.example.yoga_app.dao.YogaCourseDao

@Database(entities = [User::class, YogaCourse::class, YogaClass::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun yogaCourseDao(): YogaCourseDao
    abstract fun yogaClassDao(): YogaClassDao

    companion object {
        private const val DATABASE_NAME = "app_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    instance
                }
        }
    }
}
