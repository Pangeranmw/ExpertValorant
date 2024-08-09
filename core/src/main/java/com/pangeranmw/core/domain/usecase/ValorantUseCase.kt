package com.pangeranmw.core.domain.usecase

import com.pangeranmw.core.data.Resource
import com.pangeranmw.core.domain.model.Agent
import kotlinx.coroutines.flow.Flow

interface ValorantUseCase {
    fun getAllAgent(): Flow<Resource<List<Agent>>>
    fun getAgent(query: String): Flow<List<Agent>>
    fun getFavoriteAgent(): Flow<List<Agent>>
    fun setFavoriteAgent(agent: Agent, state: Boolean)
}