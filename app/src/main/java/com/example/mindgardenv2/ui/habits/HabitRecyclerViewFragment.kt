package com.example.mindgardenv2.ui.habits

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindgardenv2.R
import com.example.mindgardenv2.data.Habit
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.habit_recycler_view_fragment.*

@AndroidEntryPoint
class HabitRecyclerViewFragment : Fragment(R.layout.habit_recycler_view_fragment),
    HabitAdapter.OnItemClickListenerCheckbox, HabitAdapter.OnItemClickListenerTimer {

    private val habitViewModel: HabitViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val habitAdapter = HabitAdapter(this, this)

        rv_habits.apply {
            adapter = habitAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        val allHabits = habitViewModel.habits
        allHabits.observe(viewLifecycleOwner) {
            habitAdapter.submitList(it)
        }

    }


    override fun onItemClick(habit: Habit) {
        habitViewModel.onItemSelected(habit)
    }

    override fun onCheckboxClick(habit: Habit, isChecked: Boolean) {
        habitViewModel.onCheckboxSelected(habit, isChecked)
    }

    override fun onButtonClick(habit: Habit) {
        Log.d(TAG, "Button clicked")
    }

    companion object {
        const val TAG = "recycler_fragment"
    }
}