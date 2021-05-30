package com.example.mindgardenv2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Plant(val type: Int = pTypeDefault,
                 val x:Int, val y:Int, val health: Int = pHealthDefault,
                 val lifeStage: Int = pLifeStageDefault, val scale: Int,
                 @PrimaryKey(autoGenerate = true) val plantID: Int = 0) {

    companion object {
        const val pHealthDefault = 3
        val pHealthStates = arrayOf(0,1,2,3)
        const val pLifeStageDefault = 0
        val pLifeStages = arrayOf(0,1,2)

        const val pTypeDefault = 0
        val pTypes = arrayOf(0,1,2,3)
    }
}