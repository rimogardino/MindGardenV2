package com.example.mindgardenv2.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindgardenv2.data.habits.HabitDao
import com.example.mindgardenv2.data.plants.Plant
import com.example.mindgardenv2.data.plants.PlantDao
import com.example.mindgardenv2.data.session.Session
import com.example.mindgardenv2.data.session.SessionDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class GardenWorker @Inject constructor(
    private val habitDao: HabitDao,
    private val plantDao: PlantDao,
    private val sessionDao: SessionDao
) : ViewModel() {

    // I think I could use Calender in order to support every android version, but do I care?
    private val today = LocalDate.now()
    private var currentSession: Session = Session(date = today, latestSession = true)


    init {
        var isNewSession = true

        viewModelScope.launch {
            val latestSessions = sessionDao.getLatestSession()

            for (sesh in latestSessions) {
                Log.d(TAG, "today.compareTo(sesh.date) ${today.compareTo(sesh.date)}")
                if (today.compareTo(sesh.date) == 0) {
                    isNewSession = false
                    currentSession = sesh
                } else {
                    sesh.latestSession = false
                    sessionDao.updateSession(sesh)
                }
            }

            if (isNewSession) {
                currentSession.streak = isStreak()
                resetHabits()

                launch {
                    if (currentSession.streak == 0) degradeTheGarden()
                }

                Log.d(TAG, "isNewSession $isNewSession and currentSession $currentSession")
                sessionDao.insertSession(currentSession)
            }
            updateCompleteness()
        }


    }

    private fun waterTheGarden() {
        Log.d(TAG, "waterTheGarden")
        viewModelScope.launch {
            val youngPlants = plantDao.getAllYoungPlants()
            val youngPlantsCount = youngPlants.count()
            val degradedPlants = plantDao.getAllDegradedPlants()
            val degradedPlantsCount = degradedPlants.count()
            Log.d(TAG, "waterTheGarden get plant counts finished?")
            Log.d(TAG, "$youngPlantsCount")

            if (youngPlantsCount == 0 && degradedPlantsCount == 0) {
                // maybe I need a plantFactory??
                val x = (0..650).random()
                val y = (0..650).random()
                val scale: Float = 5.rangeTo(10).random() / 10f
                // Plant.pLifeStageGrown is for testing only should be removed
                val newPlant = Plant(x = x, y = y, scale = scale)
                Log.d(TAG, "waterTheGarden newPlant $newPlant?")
                plantDao.insertPlant(newPlant)
            } else if (degradedPlantsCount > 0) {
                val chosenPlant = degradedPlants[0.until(degradedPlantsCount).random()]
                chosenPlant.health += 1
                plantDao.updatePlant(chosenPlant)
            } else if (youngPlantsCount > 0) {
                Log.d(TAG, "waterTheGarden newPlant $youngPlants?")
                val chosenPlant = youngPlants[0.until(youngPlantsCount).random()]
                chosenPlant.lifeStage += 1
                plantDao.updatePlant(chosenPlant)
            }

        }

    }

    private suspend fun degradeTheGarden() {
        Log.d(TAG, "degrade plant")

        val allPlants = plantDao.getAllPlantsSynchronous()
        val chosenPlant = allPlants[(0.until(allPlants.size)).random()]
        val degradedHealth = chosenPlant.health - 1
        chosenPlant.health = degradedHealth
        Log.d(TAG, "degrade plant $chosenPlant")

        if (degradedHealth > 0) {
            plantDao.updatePlant(chosenPlant)
        } else {
            plantDao.deletePlant(chosenPlant)
        }


    }

    private suspend fun isStreak(): Int {
        val prevSession = sessionDao.getPreviousSession()
        val prevDate = prevSession.date
        val currentDate = currentSession.date

        val daysBetweenSessions = prevDate.until(currentDate, ChronoUnit.DAYS)
        Log.d(TAG, "daysBetweenSessions $daysBetweenSessions")

        return if (prevSession.completeness > 0) daysBetweenSessions.toInt() else 0
    }


    private fun resetHabits() {
        viewModelScope.launch {
            val synchronousHabits = habitDao.getAllHabitsSynchronous()

            synchronousHabits.forEach {
                it.checked = false
                habitDao.updateHabit(it)
            }
        }
    }


    private suspend fun updateCompleteness() {
        // get a percentage of how complete the tasks are
        Log.d(TAG, "updateCompleteness")
        var nOfHabits = 0
        var nCompletedHabits = 0

        val allHabits = habitDao.getAllHabits()
        allHabits.observeForever { currentHabits ->
            nOfHabits = 0
            nCompletedHabits = 0
            currentHabits.forEach {
                nOfHabits += 1
                if (it.checked) nCompletedHabits += 1
            }


            val completeness = (nCompletedHabits * 100 / nOfHabits.toFloat()).toInt()
            currentSession.completeness = completeness

            if (completeness >= Session.checkPoint_1_threshHold && !currentSession.checkPoint_1) {
                currentSession.checkPoint_1 = true
                waterTheGarden()
            }

            if (completeness >= Session.checkPoint_2_threshHold && !currentSession.checkPoint_2) {
                currentSession.checkPoint_2 = true
                waterTheGarden()
            }

            if (completeness >= Session.checkPoint_3_threshHold && !currentSession.checkPoint_3) {
                currentSession.checkPoint_3 = true
                waterTheGarden()
            }

            viewModelScope.launch {
                sessionDao.updateSession(currentSession)
                Log.d(TAG, "completeness updated to $completeness and sesssion $currentSession")
            }


        }

    }

    fun getCurrentSession(): Session {
        return currentSession
    }


    companion object {
        const val TAG = "Gardener"
    }
}
