package com.example.catfacts.data

import com.example.catfacts.data.local.JournalDao
import com.example.catfacts.data.model.Journal
import com.example.catfacts.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface JournalRepository {

    suspend fun getJournal(journalId: Long): Journal?

    fun getJournals(): Flow<List<Journal>>

    suspend fun saveJournal(title: String, description: String, pictureAbsolutePath: String?): Long
}

class DefaultJournalRepository @Inject constructor(
    private val journalDao: JournalDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : JournalRepository {

    override suspend fun getJournal(journalId: Long): Journal? = withContext(dispatcher) {
        journalDao.getJournal(journalId)
    }

    override fun getJournals() = journalDao.getJournals()

    override suspend fun saveJournal(
        title: String,
        description: String,
        pictureAbsolutePath: String?
    ) = withContext(dispatcher) {
        val journal = Journal(
            title = title,
            description = description,
            imageFilePath = pictureAbsolutePath
        )

        journalDao.insertJournal(journal)
    }
}