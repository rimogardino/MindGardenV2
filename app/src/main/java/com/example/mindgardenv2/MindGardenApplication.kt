package com.example.mindgardenv2

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MindGardenApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: MindGardenApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

}