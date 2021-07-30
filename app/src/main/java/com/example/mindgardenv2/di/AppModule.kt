package com.example.mindgardenv2.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.mindgardenv2.data.CombinedDataBase
import com.example.mindgardenv2.data.habits.Habit
import com.example.mindgardenv2.data.habits.HabitDao
import com.example.mindgardenv2.data.plants.PlantDao
import com.example.mindgardenv2.data.session.SessionDao
import com.example.mindgardenv2.ui.habits.HabitRecyclerViewFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataBase(
        app : Application,
        initCallBack: CombinedDataBase.InitCallBack
    ): CombinedDataBase {
        val db = Room.databaseBuilder(app, CombinedDataBase::class.java, "main_db")
            .allowMainThreadQueries()
            .addCallback(initCallBack)
            .fallbackToDestructiveMigration()
            .build()

        return db
    }


    @Provides
    fun providePlantDao(db: CombinedDataBase) : PlantDao = db.plantDao()

    @Provides
    fun provideHabitDao(db: CombinedDataBase) : HabitDao = db.habitDao()

    @Provides
    fun provideSessionDao(db: CombinedDataBase) : SessionDao = db.sessionDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())


}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope