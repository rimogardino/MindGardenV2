package com.example.mindgardenv2.data


import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.mindgardenv2.utils.DBconverters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Plant::class, Habit::class, Session::class], version = 1)
@TypeConverters(DBconverters::class)
abstract class CombinedDataBase : RoomDatabase() {

        abstract fun plantDao() : PlantDao
        abstract fun habitDao() : HabitDao
        abstract fun sessionDao() : SessionDao

        class InitCallBack @Inject constructor(
                private val mainDatabase: Provider<CombinedDataBase>,
                @ApplicationScope private val applicationScope: CoroutineScope
        ) : Callback() {

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val plantDao = mainDatabase.get().plantDao()
                        val habitDao = mainDatabase.get().habitDao()
                        val sessionDao = mainDatabase.get().sessionDao()

                        applicationScope.launch {
                                sessionDao.insertSession(Session(LocalDate.parse("2021-06-28"),
                                        latestSession = false))
                                sessionDao.insertSession(Session(LocalDate.parse("2021-07-01"),
                                        latestSession = true))

                                plantDao.insertPlant(Plant(x = 300, y = 300,
                                        scale = 1f, lifeStage = Plant.pLifeStageStart))
                                plantDao.insertPlant(Plant(x = 400, y = 600,
                                        scale = 1f, lifeStage = Plant.pLifeStageStart))
                                plantDao.insertPlant(Plant(x = 500, y = 500,
                                        scale = 1f, lifeStage = Plant.pLifeStageGrown))
                                plantDao.insertPlant(Plant(x = 600, y = 400,
                                        scale = 1f, lifeStage = Plant.pLifeStageGrown))

                                habitDao.insertHabit(Habit(text = "blabla"))
                                habitDao.insertHabit(Habit(text = "blabla 2"))
                                habitDao.insertHabit(Habit(text = "blabla 3"))
                                habitDao.insertHabit(Habit(text = "get high",type = Habit.typeTimer))


                        }

                }
        }
}