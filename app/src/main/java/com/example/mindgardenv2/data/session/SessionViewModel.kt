package com.example.mindgardenv2.data.session

import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject


/**
    This might make more sense in the UI package, but it also doesn't so idk
 */
@HiltViewModel
class SessionViewModel @Inject constructor(private val sessionDao: SessionDao) {

    fun getLastSession() = sessionDao.getLastSession()

    fun getSessionByDate(date: LocalDate) = sessionDao.getSessionByDate(date)

    fun getAllSessions() = sessionDao.getAllSessions()

    suspend fun insertSession(session: Session) = sessionDao.insertSession(session)

    suspend fun deleteSession(session: Session) = sessionDao.deleteSession(session)

    suspend fun updateSession(session: Session) = sessionDao.updateSession(session)
}