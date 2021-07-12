package com.example.mindgardenv2.data.session

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Entity(tableName = "session_table")
@Parcelize
data class Session(@PrimaryKey val date: LocalDate, var latestSession: Boolean = false,
                   var streak: Int = 0,
                   var completeness: Int = 0, var checkPoint_1: Boolean = false,
                   var checkPoint_2: Boolean = false, var checkPoint_3: Boolean = false) :
    Parcelable {

    companion object {
        const val checkPoint_1_threshHold = 1
        const val checkPoint_2_threshHold = 50
        const val checkPoint_3_threshHold = 100
    }
}
