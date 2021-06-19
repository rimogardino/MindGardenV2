package com.example.mindgardenv2.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.example.mindgardenv2.ui.plants.PlantViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun isNewSession(): LocalDate = LocalDate.now()
