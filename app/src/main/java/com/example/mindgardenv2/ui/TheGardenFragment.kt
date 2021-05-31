package com.example.mindgardenv2.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.mindgardenv2.R
import com.example.mindgardenv2.data.Plant
import com.example.mindgardenv2.databinding.TheGardenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TheGardenFragment : Fragment(R.layout.the_garden) {

    private val plantViewModel: PlantViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gardenBinding = TheGardenBinding.bind(view)

        val allPlants = plantViewModel.plants

        Log.d("garden_fragment", "${allPlants}")

    }
}