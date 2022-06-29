package com.example.catfacts.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.catfacts.data.model.Journal
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    @Query("SELECT * FROM table_journal where journal_id=:journalId")
    fun getJournal(journalId: Long): Journal?

    @Query("SELECT * FROM table_journal")
    fun getJournals(): Flow<List<Journal>>

    @Insert
    suspend fun insertJournal(journal: Journal): Long

    @Delete
    suspend fun deleteJournal(journal: Journal)
}