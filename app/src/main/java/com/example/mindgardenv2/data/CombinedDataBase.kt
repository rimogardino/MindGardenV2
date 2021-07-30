package com.example.mindgardenv2.data


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mindgardenv2.data.habits.Habit
import com.example.mindgardenv2.data.habits.HabitDao
import com.example.mindgardenv2.data.plants.Plant
import com.example.mindgardenv2.data.plants.PlantDao
import com.example.mindgardenv2.data.session.Session
import com.example.mindgardenv2.data.session.SessionDao
import com.example.mindgardenv2.di.ApplicationScope
import com.example.mindgardenv2.utils.DbConverters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Plant::class, Habit::class, Session::class], version = 2)
@TypeConverters(DbConverters::class)
abstract class CombinedDataBase : RoomDatabase() {

    abstract fun plantDao(): PlantDao
    abstract fun habitDao(): HabitDao
    abstract fun sessionDao(): SessionDao

    class InitCallBack @Inject constructor(
        private val mainDatabase: Provider<CombinedDataBase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val plantDao = mainDatabase.get().plantDao()
            val habitDao = mainDatabase.get().habitDao()
            //val sessionDao = mainDatabase.get().sessionDao()

            applicationScope.launch {
                plantDao.insertPlant(
                    Plant(
                        x = 100, y = 300,
                        scale = 1f, lifeStage = Plant.pLifeStageStart
                    )
                )
                plantDao.insertPlant(
                    Plant(
                        x = 600, y = 400,
                        scale = 1f, lifeStage = Plant.pLifeStageGrown
                    )
                )

                habitDao.insertHabit(Habit(text = "Do nothing", repeating = false))
                habitDao.insertHabit(Habit(text = "swipe me", repeating = false))
                habitDao.insertHabit(
                    Habit(
                        text = "Think about why would a grown" +
                                " ass man make an app with flowers?!?",
                        type = Habit.typeTimer
                    )
                )


            }

        }
    }
}