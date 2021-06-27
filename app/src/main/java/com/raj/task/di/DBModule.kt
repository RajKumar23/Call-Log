package com.raj.task.di

import android.content.Context
import com.raj.task.data.room.AppDatabase
import com.raj.task.data.room.DatabaseBuilder
import com.raj.task.data.room.DatabaseHelper
import com.raj.task.data.room.DatabaseHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {
    @Provides
    fun provideOrderDao(@ApplicationContext appContext: Context): AppDatabase {
        return DatabaseBuilder().getInstance(appContext)
    }

    @Provides
    @Singleton
    fun provideRoomHelper(databaseHelperImpl: DatabaseHelperImpl): DatabaseHelper =
        databaseHelperImpl
}