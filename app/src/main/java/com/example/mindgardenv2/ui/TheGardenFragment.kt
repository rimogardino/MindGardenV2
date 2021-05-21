package com.example.mindgardenv2.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mindgardenv2.R
import com.example.mindgardenv2.databinding.TheGardenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TheGardenFragment : Fragment(R.layout.the_garden) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gardenBinding = TheGardenBinding.bind(view)

    }
}