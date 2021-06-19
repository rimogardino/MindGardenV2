package com.example.mindgardenv2.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate


class DBconverters {

        @RequiresApi(Build.VERSION_CODES.O)
        @TypeConverter
        fun fromTimestamp(value: String?): LocalDate? {
            return value?.let { LocalDate.parse(it) }
        }

        @TypeConverter
        fun dateToTimestamp(date: LocalDate?): String? {
            return date?.toString()
        }

}