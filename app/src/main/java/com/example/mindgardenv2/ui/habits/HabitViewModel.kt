package com.example.mindgardenv2.ui.habits

import android.os.CountDownTimer
import android.util.Log
import android.widget.Spinner
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
    val habits = habitDao.getAllHabits()
    suspend fun synchronousHabits() = habitDao.getAllHabitsSynchronous()
    private var timer: CountDownTimer? = null
    private var habitWithATimer: Habit? = null

    fun onItemSelected(habit: Habit) = viewModelScope.launch {
        habitDao.updateHabit(habit)
    }

    fun onCheckboxSelected(habit: Habit, isChecked: Boolean) = viewModelScope.launch {
        if (isChecked && !habit.repeating) {
            habitDao.deleteHabit(habit)
        } else {
            habitDao.updateHabit(habit.copy(checked = isChecked))
        }
    }

    fun addNewHabit(habit: Habit) = viewModelScope.launch {
        habitDao.insertHabit(habit)
    }

    fun onHabitSwiped(habit: Habit) = viewModelScope.launch {
        habitDao.deleteHabit(habit)
    }


    fun startTimer(habit: Habit) {
        // Maybe updating a value in the database is not the best approach to keep track of the time
        // takes a whole minute to update the first time
        Log.d(TAG, "starting timer for $habit")
        if (timer != null) timer?.cancel()
        // If a different habit had a running timer reset it's values
        if (habitWithATimer != null && habitWithATimer?.habitID != habit.habitID) {
            habitWithATimer?.runningTime = 0
            viewModelScope.launch {
                habitDao.updateHabit(habitWithATimer!!)
            }
        }


        habit.runningTime = habit.time + 1
        viewModelScope.launch {
            habitDao.updateHabit(habit)
        }

        timer = object : CountDownTimer( habit.time * 60 * 1000L , 60L * 1000L ) {

            override fun onTick(millisUntilFinished: Long) {
                Log.d(TAG, "running timer at millisUntilFinished $millisUntilFinished")
                habit.runningTime -= 1
                viewModelScope.launch {
                    habitDao.updateHabit(habit)
                }
            }

            override fun onFinish() {
                habit.checked = true
                habit.runningTime = 0
                viewModelScope.launch {
                    habitDao.updateHabit(habit)
                }

                //this should probably also make a noise
            }

        }.start()



    }

    fun onTimeChange(habit: Habit) {
        viewModelScope.launch {
            habitDao.updateHabit(habit)
        }
    }

    companion object {
        const val TAG = "habit_view_model"
    }
}