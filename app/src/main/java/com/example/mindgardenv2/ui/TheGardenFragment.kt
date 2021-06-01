package com.example.mindgardenv2.ui

import android.app.Activity
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindgardenv2.R
import com.example.mindgardenv2.data.Habit
import com.example.mindgardenv2.data.Plant
import com.example.mindgardenv2.databinding.TheGardenBinding
import com.example.mindgardenv2.ui.habits.HabitAdapter
import com.example.mindgardenv2.ui.habits.HabitViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.the_garden.*

@AndroidEntryPoint
class TheGardenFragment : Fragment(R.layout.the_garden) {

    private val plantViewModel: PlantViewModel by viewModels()
    private val habitViewModel: HabitViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Habit stuff
        val allHabits = habitViewModel.habits
        val habitAdapter = HabitAdapter()

        rv_habits.apply {
            adapter = habitAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        allHabits.observe(viewLifecycleOwner) {
            habitAdapter.submitList(it)
        }


        // Plants
        val allPlants = plantViewModel.plants

        allPlants.observe(viewLifecycleOwner) {
            for (plant in it) {
                showPlant(plant)
            }
        }



        Log.d("garden_fragment", "")

    }



    private fun showPlant(plant: Plant) {
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
}