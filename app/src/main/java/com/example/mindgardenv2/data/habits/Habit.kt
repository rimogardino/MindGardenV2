package com.example.mindgardenv2.data.habits

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "habit_table")
@Parcelize
data class Habit(@PrimaryKey(autoGenerate = true) val habitID: Int = 0, val type: Int = typeCheckbox,
                 val text: String, var time: Int = 8, var checked: Boolean = false,
                 var runningTime : Int = 0, val repeating: Boolean = true) : Parcelable {

    companion object {
        const val typeTimer = 15
        const val typeCheckbox = 16
    }
}