package com.example.mindgardenv2.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "plant_table")
@Parcelize
data class Plant(
    val type: Int = pTypeDefault,
    val x: Int = 0,
    val y: Int = 0,
    val health: Int = pHealthDefault,
    val lifeStage: Int = pLifeStageDefault,
    val scale: Int = 1,
    @PrimaryKey(autoGenerate = true) val plantID: Int = 0
) : Parcelable {

    companion object {
        const val pHealthDefault = 3
        val pHealthStates = arrayOf(0,1,2,3)
        const val pLifeStageDefault = 0
        val pLifeStages = arrayOf(0,1,2)

        const val pTypeDefault = 0
        val pTypes = arrayOf(0,1,2,3)
    }
}