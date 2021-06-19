package com.example.mindgardenv2.ui.plants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindgardenv2.data.plants.Plant
import com.example.mindgardenv2.data.plants.PlantDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantViewModel @Inject constructor(private val plantDao: PlantDao): ViewModel() {

    val plants = plantDao.getAllPlants().asLiveData()

    private val degradedPlantsFlow = plantDao.getAllDegradedPlants()

    val degradedPlantsCount = degradedPlantsFlow.size


    private val youngPlantsFlow = plantDao.getAllYoungPlants()

    val youngPlantsCount =  youngPlantsFlow.size

    fun addNewPlant(plant: Plant) = viewModelScope.launch { plantDao.insertPlant(plant) }

}