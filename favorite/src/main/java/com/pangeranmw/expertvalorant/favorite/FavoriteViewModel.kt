package com.pangeranmw.expertvalorant.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.pangeranmw.core.domain.usecase.ValorantUseCase


class FavoriteViewModel(movieUseCase: ValorantUseCase) : ViewModel() {
    val favorite = movieUseCase.getFavoriteAgent().asLiveData()
}