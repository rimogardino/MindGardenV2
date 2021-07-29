package com.example.mindgardenv2.ui.plants

import androidx.lifecycle.ViewModel
import com.example.mindgardenv2.data.plants.PlantDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantViewModel @Inject constructor(plantDao: PlantDao) : ViewModel() {
    val plants = plantDao.getAllPlants()
}