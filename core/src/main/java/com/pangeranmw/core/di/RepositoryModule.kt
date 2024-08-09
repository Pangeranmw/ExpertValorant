package com.pangeranmw.core.di

import com.pangeranmw.core.data.ValorantRepository
import com.pangeranmw.core.domain.repository.IValorantRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [DatabaseModule::class, NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(valorantRepository: ValorantRepository): IValorantRepository

}