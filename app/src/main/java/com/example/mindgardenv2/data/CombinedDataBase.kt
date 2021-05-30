package com.example.mindgardenv2.data


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mindgardenv2.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Plant::class, Habit::class], version = 1)
abstract class CombinedDataBase : RoomDatabase() {

        abstract fun plantDao() : PlantDao
        abstract fun habitDao() : HabitDao

        class InitCallBack @Inject constructor(
                private val mainDatabase: Provider<CombinedDataBase>,
                @ApplicationScope private val applicationScope: CoroutineScope
        ) : Callback() {

                override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val plantDao = mainDatabase.get().plantDao()
                        val habitDao = mainDatabase.get().habitDao()

                        applicationScope.launch {
                                plantDao.insertPlant(Plant(x = 300, y = 300, scale = 1))
                                plantDao.insertPlant(Plant(x = 400, y = 600, scale = 1))
                                plantDao.insertPlant(Plant(x = 500, y = 500, scale = 1))
                                plantDao.insertPlant(Plant(x = 600, y = 400, scale = 1))

                                habitDao.insertHabit(Habit(text = "blabla"))
                                habitDao.insertHabit(Habit(text = "get high"))
                        }

                }
        }
}