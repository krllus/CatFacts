package com.example.catfacts.di

import com.example.catfacts.api.CatFactsApi
import com.example.catfacts.data.CapturesRepository
import com.example.catfacts.data.DefaultCapturesRepository
import com.example.catfacts.data.DefaultFactRepository
import com.example.catfacts.data.FactRepository
import com.example.catfacts.data.local.CapturesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [ViewModelModule::class])
object AppModule {

    @Provides
    @Singleton
    fun provideCatFactsApi(): CatFactsApi {
        return CatFactsApi.create()
    }

    @Provides
    fun provideFactsRepository(
        api: CatFactsApi,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): FactRepository {
        return DefaultFactRepository(
            api, dispatcher
        )
    }

    @Provides
    fun provideCapturesRepository(
        capturesDao: CapturesDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): CapturesRepository {
        return DefaultCapturesRepository(
            capturesDao,
            dispatcher
        )
    }

}