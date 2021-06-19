package com.example.mindgardenv2.utils

import com.example.mindgardenv2.data.plants.Plant
import com.example.mindgardenv2.ui.plants.PlantViewModel
import javax.inject.Inject


class GardenWorker @Inject constructor(private val plantViewModel: PlantViewModel) {

    fun waterTheGarden() {
                if (plantViewModel.youngPlantsCount == 0 && plantViewModel.degradedPlantsCount == 0) {
                    // maybe I need a plantFactory??
                    val newPlant = Plant(x= 666,y=666)
                    plantViewModel.addNewPlant(newPlant)
                }

    }

}