package com.example.mindgardenv2.di

import android.app.Application
import androidx.room.Room
import com.example.mindgardenv2.data.CombinedDataBase
import com.example.mindgardenv2.data.habits.HabitDao
import com.example.mindgardenv2.data.plants.PlantDao
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
    ) = Room.databaseBuilder(app, CombinedDataBase::class.java,"main_db")
            .fallbackToDestructiveMigration()
            .addCallback(initCallBack)
            .build()

    @Provides
    fun providePlantDao(db: CombinedDataBase) : PlantDao = db.plantDao()

    @Provides
    fun provideHabitDao(db: CombinedDataBase) : HabitDao = db.habitDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope