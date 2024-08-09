package com.pangeranmw.core.data.source.local

import com.pangeranmw.core.data.source.local.entity.AgentEntity
import com.pangeranmw.core.data.source.local.room.ValorantDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val valorantDao: ValorantDao) {

    fun getAllAgent(): Flow<List<AgentEntity>> = valorantDao.getAllAgent()

    fun getFavoriteAgent(): Flow<List<AgentEntity>> = valorantDao.getFavoriteAgent()
    fun getAgent(query: String): Flow<List<AgentEntity>> = valorantDao.getAgent(query)

    suspend fun insertAgent(agentList: List<AgentEntity>) = valorantDao.insertAgent(agentList)

    fun setFavoriteAgent(tourism: AgentEntity, newState: Boolean) {
        tourism.isFavorite = newState
        valorantDao.updateFavoriteAgent(tourism)
    }
}