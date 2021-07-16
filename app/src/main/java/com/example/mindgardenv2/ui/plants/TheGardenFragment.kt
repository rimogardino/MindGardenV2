package com.example.mindgardenv2.ui.plants


import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import com.example.mindgardenv2.R
import com.example.mindgardenv2.data.plants.Plant
import com.example.mindgardenv2.utils.GardenWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.the_garden.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TheGardenFragment : Fragment(R.layout.the_garden) {
    // not sure about passing through all the viewModels
    //private val sessionViewModel: SessionViewModel by viewModels()
    private val plantViewModel: PlantViewModel by viewModels()
    private val gardenWorker: GardenWorker by viewModels()
    private var staticAllPlants = mapOf<Int, Plant>()
    private var staticAllPlantsssd = listOf<List<Plant>>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(lifecycleScope.coroutineContext).launch {
            staticAllPlants = plantViewModel.plants.asFlow().toList().map {
                it.map { plant -> plant.plantID to plant }.toMap()
            }[0]
        }

        showAllPlants()
        gardenWorker
    }


    private fun showAllPlants() {
        // when a plant is updated it replaces it in the data base and the ID changes so it shows it in the same place again
        // Plants
        val allPlants = plantViewModel.plants

        allPlants.observe(viewLifecycleOwner) {
            CoroutineScope(lifecycleScope.coroutineContext).launch {
                for (plant in it) {

                    if (staticAllPlants.isEmpty()) {
                        showPlant(plant)
                    } else if (!staticAllPlants.keys.contains(plant.plantID)) {
                        showPlant(plant)
                    } else if (cl_garden.findViewById<ImageView>(plant.plantID) != null) {
                        // if we are already showing this plant update it's state / animation
                        val shownPlant = staticAllPlants[plant.plantID]
                        if (shownPlant?.lifeStage != plant.lifeStage) {
                            val changeInStage =
                                if (shownPlant!!.lifeStage > plant.lifeStage) -1 else 1
                            showPlant(plant, changeInStage)
                        }
                    }
                }
                if (staticAllPlants.isNotEmpty() && staticAllPlants.size > it.size) {
                    val newPlantIDs = it.map { it.plantID }
                    for (plantID in staticAllPlants.keys) {
                        if (!newPlantIDs.contains(plantID)) {
                            hidePlantID(plantID)
                        }
                    }
                }

                staticAllPlants = it.map { it.plantID to it }.toMap()
                Log.d(TAG, "staticAllPlants $staticAllPlants")
            }
        }


//        allPlants.observe(viewLifecycleOwner) {
//            for (plant in it) {
//                CoroutineScope(lifecycleScope.coroutineContext).launch {
//                    showPlant(plant)
//                }
//            }
//        }

    }

    private fun hidePlantID(plantID: Int) {
        Log.d(TAG, "hiding plant id $plantID")
        cl_garden.removeView(cl_garden.getViewById(plantID))
    }

    private fun hidePlant(plant: Plant) {
        Log.d(TAG, "hiding plant id ${plant.plantID}")
        cl_garden.removeView(cl_garden.getViewById(plant.plantID))
    }

    /**
    changeInStage can be -1 if degrading, 1 if upgrading and 0 if no change
     */
    private suspend fun showPlant(plant: Plant, changeInStage: Int = 0) {
        hidePlant(plant)
        Log.d(TAG, "showing plant $plant")
        val testImageView = ImageView(requireContext())

        val params = ConstraintLayout.LayoutParams(
            (300 * plant.scale).toInt(),
            (400 * plant.scale).toInt()
        )

        testImageView.tag = "clickbleImage"

        testImageView.id = plant.plantID

        // these are the same for now
        val animations = when (changeInStage) {
            1 -> listOf(
                R.drawable.anim_growing_plant_1,
                R.drawable.anim_growing_plant_1_2,
                R.drawable.anim_growing_plant_2_3
            )
            -1 -> listOf(
                R.drawable.anim_degrading_plant_1_0,
                R.drawable.anim_degrading_plant_2_1,
                R.drawable.anim_degrading_plant_3_2
            )
            else -> listOf(
                R.drawable.anim_growing_plant_1,
                R.drawable.anim_growing_plant_2,
                R.drawable.anim_growing_plant_3
            )

        }



        testImageView.setBackgroundResource(animations[plant.lifeStage])

        val animationGrowing: AnimationDrawable = testImageView.background as AnimationDrawable

        testImageView.layoutParams = params
        testImageView.x = plant.x.toFloat()
        testImageView.y = plant.y.toFloat()

        cl_garden.addView(testImageView, params)
        animationGrowing.start()
    }

    companion object {
        const val TAG = "the_garden"

    }

}