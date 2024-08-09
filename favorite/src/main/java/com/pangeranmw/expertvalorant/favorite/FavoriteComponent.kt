package com.pangeranmw.expertvalorant.favorite

import android.content.Context
import com.pangeranmw.expertvalorant.di.FavoriteModuleDepedencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDepedencies::class])
interface FavoriteComponent {
    fun inject(fragment: FavoriteFragment)
    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDepedencies: FavoriteModuleDepedencies): Builder
        fun build(): FavoriteComponent
    }
}