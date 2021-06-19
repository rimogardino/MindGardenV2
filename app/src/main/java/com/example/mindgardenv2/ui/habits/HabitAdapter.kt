package com.example.mindgardenv2.ui.habits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.mindgardenv2.data.habits.Habit
import com.example.mindgardenv2.databinding.HabitCheckboxBinding
import com.example.mindgardenv2.databinding.HabitTimerBinding
import kotlinx.android.synthetic.main.habit_checkbox.view.*


class HabitAdapter(
    private val listenerCheckbox: OnItemClickListenerCheckbox,
    private val listenerTimer: OnItemClickListenerTimer
) : ListAdapter<Habit, HabitAdapter.HabitViewHolder>(DiffCallback()) {

    abstract class HabitViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        open fun bind(habit: Habit) {}
    }

    inner class HabitViewHolderCheckbox(private val binding: HabitCheckboxBinding) :
        HabitViewHolder(binding) {

        init {
            binding.apply {

                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val habit = getItem(position)
                        listenerCheckbox.onItemClick(habit)
                    }
                }

                checkBox.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val habit = getItem(position)

                        listenerCheckbox.onCheckboxClick(habit, it.checkBox.isChecked)
                    }
                }
            }
        }

        override fun bind(habit: Habit) {
            binding.apply {
                checkBox.text = habit.text
                checkBox.isChecked = habit.checked
            }


        }
    }

    inner class HabitViewHolderTimer(private val timerBinding: HabitTimerBinding) :
        HabitViewHolder(timerBinding) {

        init {
            timerBinding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val habit = getItem(position)
                        listenerTimer.onItemClick(habit)
                    }
                }

                buttonStartPause.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val habit = getItem(position)
                        listenerTimer.onButtonClick(habit)
                    }
                }
            }
        }

        override fun bind(habit: Habit) {
            timerBinding.apply {
                textView.text = habit.text
                spinnerTime.setSelection(habit.time - 1)
            }
        }

    }


    interface OnItemClickListenerCheckbox {
        fun onItemClick(habit: Habit)
        fun onCheckboxClick(habit: Habit, isChecked: Boolean)
    }

    interface OnItemClickListenerTimer {
        fun onItemClick(habit: Habit)
        fun onButtonClick(habit: Habit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        return if (viewType == Habit.typeTimer) {
            val binding = HabitTimerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
            HabitViewHolderTimer(binding)
        } else {
            val binding = HabitCheckboxBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
            HabitViewHolderCheckbox(binding)
        }
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val currentHabit = getItem(position)
        holder.bind(currentHabit)
    }

    override fun getItemViewType(position: Int): Int {
        val currentHabit = getItem(position)
        return currentHabit.type
    }

    class DiffCallback : DiffUtil.ItemCallback<Habit>() {
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit) =
            oldItem.habitID == newItem.habitID

        override fun areContentsTheSame(oldItem: Habit, newItem: Habit) = oldItem == newItem
    }

}