package com.example.mindgardenv2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {

    @Insert
    fun insertPlant(plant: Plant)

    @Delete
    fun deletePlant(plant: Plant)

    @Update
    fun updatePlant(plant: Plant)

    @Query("SELECT * FROM plant")
    fun getAllPlants() : Flow<List<Plant>>

}