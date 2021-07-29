package com.example.mindgardenv2.data.habits

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HabitDao {

    @Insert
    suspend fun insertHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

    @Query("SELECT * FROM habit_table")
    fun getAllHabits() : LiveData<List<Habit>>

    @Query("SELECT * FROM habit_table")
    suspend fun getAllHabitsSynchronous() : List<Habit>
}