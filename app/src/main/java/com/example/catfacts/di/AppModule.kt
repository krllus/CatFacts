package com.example.catfacts.di

import com.example.catfacts.api.CatFactsApi
import com.example.catfacts.data.DefaultFactRepository
import com.example.catfacts.data.FactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideCatFactsApi(): CatFactsApi {
        return CatFactsApi.create()
    }

    @Provides
    fun provideFactRepository(
        api: CatFactsApi,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): FactRepository {
        return DefaultFactRepository(
            api, dispatcher
        )
    }

}