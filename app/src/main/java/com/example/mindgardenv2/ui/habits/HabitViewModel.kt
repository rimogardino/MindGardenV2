package com.example.mindgardenv2.ui.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.mindgardenv2.data.HabitDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(habitDao: HabitDao) : ViewModel() {
    val habits = habitDao.getAllHabits().asLiveData()
}