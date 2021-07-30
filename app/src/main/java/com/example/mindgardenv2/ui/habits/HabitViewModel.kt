package com.example.mindgardenv2.ui.habits

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindgardenv2.data.habits.Habit
import com.example.mindgardenv2.data.habits.HabitDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(private val habitDao: HabitDao) : ViewModel() {
    val habits = habitDao.getAllHabits()

    //suspend fun synchronousHabits() = habitDao.getAllHabitsSynchronous()
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
        Log.d(TAG, "starting timer for $habitWithATimer $habit")

        // If a different habit had a running timer reset it's values
        //if (habitWithATimer != null && habitWithATimer?.habitID != habit.habitID) {
        // If a timer was already running just cancel everything
        if (habitWithATimer != null && timer != null) {
            habitWithATimer?.runningTime = "Start"
            habit.runningTime = "Start"
            Log.d(TAG, "canceling timer for $habit")
            timer?.cancel()
            viewModelScope.launch {
                habitDao.updateHabit(habitWithATimer!!)
                habitDao.updateHabit(habit)
            }
            habitWithATimer = null
            return
        }


        habit.runningTime = habit.time.toString()
        habitWithATimer = habit
        viewModelScope.launch {
            habitDao.updateHabit(habit)
        }

        val milliWeight = 1000L
        timer = object : CountDownTimer(habit.time * 60 * milliWeight, 60 * milliWeight) {


            override fun onTick(millisUntilFinished: Long) {
                habit.runningTime = ((millisUntilFinished / (60 * milliWeight)) + 1).toString()//(habit.runningTime.toInt() - 1).toString()
                viewModelScope.launch {
                    habitDao.updateHabit(habit)
                }
            }

            override fun onFinish() {
                habit.checked = true
                habit.runningTime = "Start"
                habitWithATimer = null
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