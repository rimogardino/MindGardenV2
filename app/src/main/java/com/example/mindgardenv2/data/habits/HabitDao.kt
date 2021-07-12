package com.example.mindgardenv2.data.habits

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Insert
    suspend fun insertHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

    @Query("SELECT * FROM habit_table")
    fun getAllHabits() : Flow<List<Habit>>

    @Query("SELECT * FROM habit_table")
    suspend fun getAllHabitsSynchronous() : List<Habit>
}