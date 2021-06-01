package com.example.mindgardenv2.ui.habits


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mindgardenv2.data.Habit
import com.example.mindgardenv2.databinding.HabitCheckboxBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count

class HabitAdapter : ListAdapter<Habit, HabitAdapter.HabitViewHolder>(DiffCallback()) {

    class HabitViewHolder(private val binding: HabitCheckboxBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(habit: Habit) {
            binding.apply {
                this.checkBox.text = habit.text
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = HabitCheckboxBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val currentHabit = getItem(position)
        holder.bind(currentHabit)
    }

    class DiffCallback : DiffUtil.ItemCallback<Habit>() {
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit)  = oldItem.habitID == newItem.habitID

        override fun areContentsTheSame(oldItem: Habit, newItem: Habit) = oldItem == newItem
    }

}