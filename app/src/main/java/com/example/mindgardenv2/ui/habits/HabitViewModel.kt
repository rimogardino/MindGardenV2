package com.example.mindgardenv2.ui.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindgardenv2.data.habits.Habit
import com.example.mindgardenv2.data.habits.HabitDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(private val habitDao: HabitDao) : ViewModel() {
    val habits = habitDao.getAllHabits().asLiveData()

    fun onItemSelected(habit: Habit) = viewModelScope.launch {
        habitDao.updateHabit(habit)
    }

    fun onCheckboxSelected(habit: Habit, isChecked: Boolean) = viewModelScope.launch {
        habitDao.updateHabit(habit.copy(checked = isChecked))
    }

    fun addNewHabit(habit: Habit) = viewModelScope.launch {
        habitDao.insertHabit(habit)
    }

    fun onHabitSwiped(habit: Habit) = viewModelScope.launch {
        habitDao.deleteHabit(habit)
    }
}