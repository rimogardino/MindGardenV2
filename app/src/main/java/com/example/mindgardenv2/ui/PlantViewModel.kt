package com.example.mindgardenv2.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.mindgardenv2.data.Plant
import com.example.mindgardenv2.data.PlantDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PlantViewModel @Inject constructor(plantDao: PlantDao): ViewModel() {

    val plants = plantDao.getAllPlants().asLiveData()


}