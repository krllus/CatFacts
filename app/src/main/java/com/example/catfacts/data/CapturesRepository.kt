package com.example.catfacts.data

import com.example.catfacts.data.local.CapturesDao
import com.example.catfacts.data.model.Capture
import com.example.catfacts.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CapturesRepository {
    fun getCaptures(): Flow<List<Capture>>

    suspend fun addRandomCapture(): Capture
}

class DefaultCapturesRepository @Inject constructor(
    private val capturesDao: CapturesDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : CapturesRepository {

    override fun getCaptures() = capturesDao.getCaptures()
    override suspend fun addRandomCapture(): Capture {
        val capture = Capture.randomCapture()
        capturesDao.insertCapture(capture)
        return capture
    }
}