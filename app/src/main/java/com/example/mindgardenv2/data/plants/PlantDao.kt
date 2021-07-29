package com.example.mindgardenv2.data.plants

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlantDao {

    @Insert
    suspend fun insertPlant(plant: Plant)

    @Delete
    suspend fun deletePlant(plant: Plant)

    @Update
    suspend fun updatePlant(plant: Plant)


    @Query("SELECT * FROM plant_table WHERE plantID = :plantID")
    fun getPlantByID(plantID: Int): Plant

    @Query("SELECT * FROM plant_table")
    fun getAllPlants(): LiveData<List<Plant>>

    @Query("SELECT * FROM plant_table")
    suspend fun getAllPlantsSynchronous(): List<Plant>

    @Query("SELECT * FROM plant_table WHERE health < ${Plant.pHealthMax}")
    suspend fun getAllDegradedPlants(): List<Plant>

    @Query("SELECT * FROM plant_table WHERE lifeStage < ${Plant.pLifeStageGrown}")
    suspend fun getAllYoungPlants(): List<Plant>

}