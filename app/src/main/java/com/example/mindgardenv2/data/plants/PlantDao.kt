package com.example.mindgardenv2.data.plants

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {

    @Insert
    suspend fun insertPlant(plant: Plant)

    @Delete
    suspend fun deletePlant(plant: Plant)

    @Update
    suspend fun updatePlant(plant: Plant)


    @Query("SELECT * FROM plant_table WHERE plantID = :plantID")
    fun getPlantByID(plantID: Int) : Plant

    @Query("SELECT * FROM plant_table")
    fun getAllPlants() : Flow<List<Plant>>

    @Query("SELECT * FROM plant_table WHERE health < ${Plant.pHealthMax}")
    fun getAllDegradedPlants() : List<Plant>

    @Query("SELECT * FROM plant_table WHERE lifeStage < ${Plant.pLifeStageGrown}")
    fun getAllYoungPlants() : List<Plant>

}