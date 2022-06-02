package com.example.catfacts.data

import com.example.catfacts.api.CatFactsApi
import com.example.catfacts.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

interface FactRepository {
    suspend fun getFact(): Response<Fact>
}

class DefaultFactRepository @Inject constructor(
    private val api: CatFactsApi,
    @IoDispatcher private val defaultDispatcher: CoroutineDispatcher
) : FactRepository {

    override suspend fun getFact(): Response<Fact> {

        return withContext(defaultDispatcher) {
            api.getFact()
        }
    }

}