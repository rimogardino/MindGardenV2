package com.example.mindgardenv2.ui.habits


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mindgardenv2.data.Habit
import com.example.mindgardenv2.databinding.HabitCheckboxBinding
import kotlinx.android.synthetic.main.habit_checkbox.view.*


class HabitAdapter(private val listener: OnItemClickListener) : ListAdapter<Habit, HabitAdapter.HabitViewHolder>(DiffCallback()) {

    inner class HabitViewHolder(private val binding: HabitCheckboxBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {

                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val habit = getItem(position)
                        listener.onItemClick(habit)
                    }
                }

                checkBox.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val habit = getItem(position)

                        listener.onCheckboxClick(habit, it.checkBox.isChecked)
                    }
                }
            }
        }

        fun bind(habit: Habit) {
            binding.apply {
                checkBox.text = habit.text
                checkBox.isChecked = habit.checked
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(habit: Habit)
        fun onCheckboxClick(habit: Habit, isChecked: Boolean)

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