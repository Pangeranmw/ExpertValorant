package com.pangeranmw.core.di

import android.content.Context
import androidx.room.Room
import com.pangeranmw.core.data.source.local.room.ValorantDao
import com.pangeranmw.core.data.source.local.room.ValorantDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ValorantDatabase =
        Room.databaseBuilder(
            context,
            ValorantDatabase::class.java,
            "Valorant.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideValorantDao(database: ValorantDatabase): ValorantDao = database.valorantDao()
}