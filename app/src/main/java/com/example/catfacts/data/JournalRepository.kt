package com.example.catfacts.data

import com.example.catfacts.data.local.JournalDao
import com.example.catfacts.data.model.Journal
import com.example.catfacts.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface JournalRepository {
    fun getJournals(): Flow<List<Journal>>
    suspend fun addRandomJournal(): Journal
}

class DefaultJournalRepository @Inject constructor(
    private val journalDao: JournalDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : JournalRepository {

    override fun getJournals() = journalDao.getJournals()
    override suspend fun addRandomJournal(): Journal {
        val journal = Journal.randomCapture()
        journalDao.insertJournal(journal)
        return journal
    }
}