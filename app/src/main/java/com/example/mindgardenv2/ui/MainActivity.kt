package com.example.mindgardenv2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.mindgardenv2.R
import com.example.mindgardenv2.data.Habit
import com.example.mindgardenv2.ui.habits.AddHabitFragment
import com.example.mindgardenv2.ui.habits.HabitViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.add_habit_fragment.view.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
//    private val habitViewModel: HabitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showAddHabit(view: View) {
        val dialog = AddHabitFragment()
        dialog.show(supportFragmentManager, "AddHabitFragment")
    }

//    fun addHabit(view: View) {
//        val newHabit = Habit(text = view.et_habit_name.text.toString())
//        habitViewModel.addNewHabit(newHabit)
//    }


}