package com.pangeranmw.core.domain.usecase

import com.pangeranmw.core.domain.model.Agent
import com.pangeranmw.core.domain.repository.IValorantRepository
import javax.inject.Inject

class ValorantInteractor @Inject constructor(private val valorantRepository: IValorantRepository): ValorantUseCase {

    override fun getAllAgent() = valorantRepository.getAllAgent()
    override fun getAgent(query: String) = valorantRepository.getAgent(query)
    override fun getFavoriteAgent() = valorantRepository.getFavoriteAgent()
    override fun setFavoriteAgent(agent: Agent, state: Boolean) = valorantRepository.setFavoriteAgent(agent, state)
}