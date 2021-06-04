package com.example.mindgardenv2.ui.plants

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log

import android.view.View

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.mindgardenv2.R
import com.example.mindgardenv2.data.Plant
import com.example.mindgardenv2.di.ApplicationScope

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.the_garden.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TheGardenFragment : Fragment(R.layout.the_garden) {

    private val plantViewModel:PlantViewModel by viewModels()

    // Sometimes on resume the animation is played again, but the old images are still visible
    // not sure why, but it might be something with the AnimationDrawable in showPlant
//    override fun onResume() {
//        super.onResume()
//        cl_garden.removeAllViews()
//        showAllPlants()
//    }
//


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showAllPlants()
    }

    private fun showAllPlants() {
        // Plants
        val allPlants = plantViewModel.plants

        allPlants.observe(viewLifecycleOwner) {
            for (plant in it) {
                CoroutineScope(lifecycleScope.coroutineContext).launch {
                    showPlant(plant)
                }
            }
        }

    }

//    private fun clearAllPlants() {
//        cl_garden.removeAllViews()
//    }

    private suspend fun showPlant(plant: Plant) {
        Log.d(TAG, "showing plant id ${plant.plantID}")
        val testImageView = ImageView(requireContext())

        val params = ConstraintLayout.LayoutParams(plant.x, plant.y)

        testImageView.tag = "clickbleImage"
        testImageView.id = plant.plantID

        // these are the same for now
        val animations = listOf(R.drawable.anim_growing_plant_1,
            R.drawable.anim_growing_plant_1,
            R.drawable.anim_growing_plant_1)

        testImageView.setBackgroundResource(animations[plant.health - 1])

        val animationGrowing : AnimationDrawable = testImageView.background as AnimationDrawable


        testImageView.layoutParams = params
        testImageView.x = plant.x.toFloat()
        testImageView.y = plant.y.toFloat()

        cl_garden.addView(testImageView,params)
        animationGrowing.start()
    }

    companion object {
        const val TAG = "the_garden"

    }

}