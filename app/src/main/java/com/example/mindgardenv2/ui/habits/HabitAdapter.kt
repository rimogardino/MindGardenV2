package com.example.mindgardenv2.ui.habits

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.mindgardenv2.R
import com.example.mindgardenv2.data.habits.Habit
import com.example.mindgardenv2.databinding.HabitCheckboxBinding
import com.example.mindgardenv2.databinding.HabitTimerBinding
import kotlinx.android.synthetic.main.habit_checkbox.view.*
import kotlinx.android.synthetic.main.habit_timer.view.*


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
                        it.button_start_pause.text = habit.time.toString()
                        listenerTimer.onButtonClick(habit)
                    }
                }

                timerLength.addTextChangedListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val habit = getItem(position)
                        if ( it!!.isNotEmpty() && it.single().digitToIntOrNull() != null ) {
                            habit.time = it.single().digitToInt()
                            listenerTimer.onTimeChange(habit)
                        }
                    }
                }

//                spinnerTime.setOnItemClickListener { parent, view, position, id ->
//                    //val position = adapterPosition
//                    if (position != RecyclerView.NO_POSITION) {
//                        val habit = getItem(position)
//
//                        listenerSpinner.onItemSpinnerSelected(habit, spinnerTime.selectedItemPosition)
//                    }
//
//                }
            }
        }

        override fun bind(habit: Habit) {
            timerBinding.apply {
                textView.text = habit.text
                timerLength.text = Editable.Factory.getInstance().newEditable("${habit.time}")
                //spinnerTime.setSelection(habit.time - 1)

                if (habit.runningTime > 0) {
                    buttonStartPause.text = habit.runningTime.toString()
                }
                else {
                    buttonStartPause.text = "Start"//Resources.getSystem().getString(R.string.start)
                }
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
        fun onTimeChange(habit: Habit)
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