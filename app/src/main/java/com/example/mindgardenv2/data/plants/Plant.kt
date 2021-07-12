package com.example.mindgardenv2.data.plants

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
    var health: Int = pHealthMax,
    var lifeStage: Int = pLifeStageStart,
    val scale: Float = 1.0f,
    @PrimaryKey(autoGenerate = true) val plantID: Int = 0
) : Parcelable {

    companion object {

        const val pHealthMax = 3
        val pHealthStates = 0..pHealthMax

        const val pLifeStageStart = 0
        const val pLifeStageGrown = 2
        val pLifeStages = pLifeStageStart..pLifeStageGrown


        const val pTypeDefault = 0
        val pTypes = arrayOf(0,1,2,3)
    }
}