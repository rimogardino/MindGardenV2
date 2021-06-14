package com.example.mindgardenv2.ui

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindgardenv2.R
import com.example.mindgardenv2.data.Habit
import com.example.mindgardenv2.data.Plant
import com.example.mindgardenv2.ui.habits.AddHabitFragment
import com.example.mindgardenv2.ui.habits.HabitAdapter
import com.example.mindgardenv2.ui.habits.HabitRecyclerViewFragment
import com.example.mindgardenv2.ui.habits.HabitViewModel
import com.example.mindgardenv2.ui.plants.PlantViewModel
import com.example.mindgardenv2.ui.plants.TheGardenFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.habit_recycler_view_fragment.*
import kotlinx.android.synthetic.main.habit_recycler_view_fragment.view.*
import kotlinx.android.synthetic.main.the_garden.*

@AndroidEntryPoint
class TheMainFragment : Fragment(R.layout.the_main_layout) {

}