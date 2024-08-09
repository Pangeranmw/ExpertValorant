package com.pangeranmw.expertvalorant.di

import com.pangeranmw.core.domain.usecase.ValorantInteractor
import com.pangeranmw.core.domain.usecase.ValorantUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideValorantUseCase(movieUseCaseImpl: ValorantInteractor): ValorantUseCase
}