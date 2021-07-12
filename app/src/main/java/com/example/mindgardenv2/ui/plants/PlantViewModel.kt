package com.example.mindgardenv2.ui.plants

import androidx.lifecycle.ViewModel
import com.example.mindgardenv2.data.plants.PlantDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantViewModel @Inject constructor(private val plantDao: PlantDao) : ViewModel() {

    val plants = plantDao.getAllPlants()

//    //suspend fun getAllPlantsOnce() = plantDao.getAllPlantsSynchronous()
//    //val plantList = viewModelScope.launch { plantDao.getAllPlantsSynchronous() }
//
//    val degradedPlantsCount = viewModelScope.launch { plantDao.getAllDegradedPlants().count() }
//
//
//    val youngPlantsCount = viewModelScope.launch { plantDao.getAllYoungPlants().count() }
//
//
//    fun addNewPlant(plant: Plant) = viewModelScope.launch { plantDao.insertPlant(plant) }

}