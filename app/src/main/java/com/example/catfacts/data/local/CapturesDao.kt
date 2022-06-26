package com.example.catfacts.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.catfacts.data.model.Capture
import kotlinx.coroutines.flow.Flow

@Dao
interface CapturesDao {

    @Query("SELECT * FROM captures_table")
    fun getCaptures(): Flow<List<Capture>>

    @Insert
    suspend fun insertCapture(capture: Capture): Long

    @Delete
    suspend fun deleteCapture(capture: Capture)
}