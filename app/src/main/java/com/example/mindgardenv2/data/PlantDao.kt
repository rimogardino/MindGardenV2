package com.example.mindgardenv2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {

    // try suspend to see if it actually makes a difference
    // i mean if it is not actually being suspended
    @Insert
    fun insertPlant(plant: Plant)

    @Delete
    fun deletePlant(plant: Plant)

    @Update
    fun updatePlant(plant: Plant)
    // My db table is not called plant apperantly
    @Query("SELECT plantID FROM plant")
    fun getPlantByID(plant: Plant) : Plant

    @Query("SELECT * FROM plant")
    fun getAllPlants() : Flow<List<Plant>>

}