package com.example.mindgardenv2.ui.habits

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mindgardenv2.R
import com.example.mindgardenv2.data.habits.Habit
import com.example.mindgardenv2.databinding.HabitRecyclerViewFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HabitRecyclerViewFragment :
    Fragment(R.layout.habit_recycler_view_fragment),
    HabitAdapter.OnItemClickListenerCheckbox, HabitAdapter.OnItemClickListenerTimer {

    private val habitViewModel: HabitViewModel by viewModels()
    //private val gardenWorker : GardenWorker by inject()
    //private val gardenWorker: GardenWorker by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = HabitRecyclerViewFragmentBinding.bind(view)
        val habitAdapter = HabitAdapter(this, this)

        binding.apply {
            rvHabits.apply {
                adapter = habitAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val habit = habitAdapter.currentList[viewHolder.adapterPosition]
                    habitViewModel.onHabitSwiped(habit)
                }
            }).attachToRecyclerView(rvHabits)
        }


        val allHabits = habitViewModel.habits
        allHabits.observe(viewLifecycleOwner) {
            habitAdapter.submitList(it)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(habit: Habit) {
        habitViewModel.onItemSelected(habit)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCheckboxClick(habit: Habit, isChecked: Boolean) {
        habitViewModel.onCheckboxSelected(habit, isChecked)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onButtonClick(habit: Habit) {
        Log.d(TAG, "Button clicked")
        // temporary fortesting
        habitViewModel.onCheckboxSelected(habit, !habit.checked)
    }

    companion object {
        const val TAG = "recycler_fragment"
    }
}