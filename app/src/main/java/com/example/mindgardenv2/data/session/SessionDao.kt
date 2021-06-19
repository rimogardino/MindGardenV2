package com.example.mindgardenv2.data.session

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface SessionDao {
    @Insert
    suspend fun insertSession(session: Session)

    @Delete
    suspend fun deleteSession(session: Session)

    @Update
    suspend fun updateSession(session: Session)

    @Query("SELECT * FROM session_table")
    fun getAllSessions() : Flow<List<Session>>

    @Query("SELECT * FROM session_table WHERE :date = 'date'")
    fun getSessionByDate(date: LocalDate) : Session

    //This should return just one session, but idk if I can guarantee it will
    @Query("SELECT * FROM session_table WHERE latestSession = 'True'")
    fun getLastSession() : Session

}