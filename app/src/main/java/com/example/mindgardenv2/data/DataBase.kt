package com.example.mindgardenv2.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Plant::class, Habit::class], version = 1)
abstract class DataBase : RoomDatabase() {

        abstract fun plantDao() : PlantDao
        abstract fun habitDao() : HabitDao
}