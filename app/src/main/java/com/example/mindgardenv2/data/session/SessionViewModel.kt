package com.example.mindgardenv2.data.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


/**
    This might make more sense in the UI package, but it also doesn't so idk
 */
@HiltViewModel
class SessionViewModel @Inject constructor(private val sessionDao: SessionDao) : ViewModel() {

    suspend fun getLatestSession() =  sessionDao.getLatestSession()

    suspend fun getPreviousSession() = sessionDao.getPreviousSession()

    fun getSessionByDate(date: LocalDate) = viewModelScope.launch { sessionDao.getSessionByDate(date) }

    suspend fun getAllSessions() = sessionDao.getAllSessions()

    suspend fun insertSession(session: Session) = viewModelScope.launch {
        sessionDao.insertSession(session) }

    suspend fun deleteSession(session: Session) = sessionDao.deleteSession(session)

    suspend fun updateSession(session: Session) = sessionDao.updateSession(session)
}

