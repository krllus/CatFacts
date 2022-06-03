package com.example.catfacts.data

import com.example.catfacts.api.CatFactsApi
import com.example.catfacts.data.remote.Result
import com.example.catfacts.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface FactRepository {
    suspend fun getFact(): Result<Fact?>
}

class DefaultFactRepository @Inject constructor(
    private val api: CatFactsApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FactRepository {
    override suspend fun getFact(): Result<Fact?> = withContext(dispatcher) {

        try {
            val response = api.getFact()

            val body = response.body()

            if (!response.isSuccessful) {
                throw Exception("Fail to download fact.")
            }

            Result.Success(body)
        } catch (e: Exception) {
            Result.Failure(e)
        }

    }


}