package com.example.mindgardenv2

import android.util.Log
import com.example.mindgardenv2.utils.isNewSession
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun newSeesionWorks() {
        isNewSession()
        println("${isNewSession()}")
    }
}