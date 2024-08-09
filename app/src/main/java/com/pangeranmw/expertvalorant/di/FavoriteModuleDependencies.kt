package com.pangeranmw.expertvalorant.di

import com.pangeranmw.core.domain.usecase.ValorantUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDepedencies {

    fun valorantUseCase(): ValorantUseCase

}