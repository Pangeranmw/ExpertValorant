package com.pangeranmw.core.data

import com.pangeranmw.core.data.source.local.LocalDataSource
import com.pangeranmw.core.data.source.remote.RemoteDataSource
import com.pangeranmw.core.data.source.remote.network.ApiResponse
import com.pangeranmw.core.data.source.remote.response.AgentItem
import com.pangeranmw.core.domain.model.Agent
import com.pangeranmw.core.domain.repository.IValorantRepository
import com.pangeranmw.core.utils.AppExecutors
import com.pangeranmw.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValorantRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IValorantRepository {

    override fun getAllAgent(): Flow<Resource<List<Agent>>> =
        object : NetworkBoundResource<List<Agent>, List<AgentItem>>() {
            override fun loadFromDB(): Flow<List<Agent>> {
                return localDataSource.getAllAgent().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Agent>?): Boolean =
                data.isNullOrEmpty()
//                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<List<AgentItem>>> =
                remoteDataSource.getAgent()

            override suspend fun saveCallResult(data: List<AgentItem>) {
                val agentList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertAgent(agentList)
            }
        }.asFlow()

    override fun getFavoriteAgent(): Flow<List<Agent>> {
        return localDataSource.getFavoriteAgent().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getAgent(query: String): Flow<List<Agent>> {
        return flow { emit(DataMapper.mapEntitiesToDomain(localDataSource.getAgent(query).first())) }.flowOn(Dispatchers.IO)
    }

    override fun setFavoriteAgent(agent: Agent, state: Boolean) {
        val agentEntity = DataMapper.mapDomainToEntity(agent)
        appExecutors.diskIO().execute { localDataSource.setFavoriteAgent(agentEntity, state) }
    }
}