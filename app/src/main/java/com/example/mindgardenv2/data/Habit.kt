package com.example.mindgardenv2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Habit(@PrimaryKey(autoGenerate = true) val habitID: Int, val type: Int, val text: String,
                    val time: Int = 8) {

    companion object {
        const val typeTimer = 15
        const val typeCheckbox = 16
    }
}