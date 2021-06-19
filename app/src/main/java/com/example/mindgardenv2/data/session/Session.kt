package com.example.mindgardenv2.data.session

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Entity(tableName = "session_table")
@Parcelize
data class Session(@PrimaryKey val date: LocalDate, val latestSession: Boolean = false,
                   val completeness: Int = 0, val checkPoint_1: Boolean = false,
                   val checkPoint_2: Boolean = false) :
    Parcelable {

    companion object {
        const val checkPoint_1_threshHold = 70
        const val checkPoint_2_threshHold = 100
    }
}
