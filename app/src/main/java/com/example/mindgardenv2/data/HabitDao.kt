package com.example.mindgardenv2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Insert
    fun insertHabit(habit: Habit)

    @Delete
    fun deleteHabit(habit: Habit)

    @Update
    fun updateHabit(habit: Habit)

    @Query("SELECT * FROM habit")
    fun getAllHabits() : Flow<List<Habit>>

}