package com.example.mindgardenv2.ui.habits

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.mindgardenv2.R
import com.example.mindgardenv2.data.habits.Habit
import com.example.mindgardenv2.databinding.AddHabitFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddHabitFragment : DialogFragment() {
    private val habitViewModel: HabitViewModel by viewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val binding = AddHabitFragmentBinding.inflate(LayoutInflater.from(context))
            binding.tbHabitRepeating.isChecked = true

            builder.setView(binding.root)
                // Add action buttons
                .setPositiveButton(
                    R.string.add
                ) { _, _ ->
                    val newHabitText = binding.etHabitName.text.toString()
                    if (newHabitText.isNotEmpty()) {
                        val habitType =
                            if (binding.tbHabitType.isChecked) Habit.typeTimer else Habit.typeCheckbox
                        val newHabit = Habit(
                            text = newHabitText, type = habitType,
                            repeating = binding.tbHabitRepeating.isChecked
                        )

                        habitViewModel.addNewHabit(newHabit)
                    }
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ ->
                    dialog?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


}