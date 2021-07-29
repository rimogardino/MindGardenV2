package com.example.mindgardenv2.data.session

import androidx.room.*
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
    suspend fun getAllSessions(): List<Session>

    @Query("SELECT * FROM session_table WHERE date = :date")
    suspend fun getSessionByDate(date: LocalDate): Session

    //This should return just one session, but idk if I can guarantee it will
    // why is TRUE red ?!?!? o_O
    @Query("SELECT * FROM session_table WHERE latestSession = TRUE")
    suspend fun getLatestSession(): List<Session>

    //SELECT * FROM session_table WHERE latestSession <> TRUE ORDER BY date ASC LIMIT 1
    @Query("SELECT * FROM session_table WHERE latestSession <> TRUE ORDER BY date DESC LIMIT 1")
    suspend fun getPreviousSession(): Session
}