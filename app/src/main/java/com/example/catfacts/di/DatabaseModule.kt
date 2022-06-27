package com.example.catfacts.di

import android.content.Context
import com.example.catfacts.data.local.AppDatabase
import com.example.catfacts.data.local.JournalDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase {
        return AppDatabase.getInstance(appContext)
    }

    @Provides
    fun provideCapturesDao(database: AppDatabase): JournalDao {
        return database.journalDao()
    }

}