package com.example.tbcacademy.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tbcacademy.data.local.dao.UserDao
import com.example.tbcacademy.data.local.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}