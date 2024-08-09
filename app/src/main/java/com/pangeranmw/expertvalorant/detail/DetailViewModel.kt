package com.pangeranmw.expertvalorant.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.pangeranmw.core.domain.model.Agent
import com.pangeranmw.core.domain.usecase.ValorantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val valorantUseCase: ValorantUseCase) : ViewModel() {
    fun setFavoriteAgent(agent: Agent, isFavorite: Boolean) =
        valorantUseCase.setFavoriteAgent(agent, isFavorite)
    val favorite = valorantUseCase.getFavoriteAgent().asLiveData()
    fun agent(query: String) = valorantUseCase.getAgent(query).asLiveData()
}