package com.example.mindgardenv2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {

    // try suspend to see if it actually makes a difference
    // i mean if it is not actually being suspended
    @Insert
    suspend fun insertPlant(plant: Plant)

    @Delete
    suspend fun deletePlant(plant: Plant)

    @Update
    suspend fun updatePlant(plant: Plant)



    @Query("SELECT * FROM plant_table")
    fun getAllPlants() : Flow<List<Plant>>

}