package com.example.mindgardenv2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mindgardenv2.R
import com.example.mindgardenv2.ui.habits.AddHabitFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showAddHabit(view: View) {
        val dialog = AddHabitFragment()
        dialog.show(supportFragmentManager, "AddHabitFragment")
    }


}