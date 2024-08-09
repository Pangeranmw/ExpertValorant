package com.pangeranmw.core.data.source.remote.network

import com.pangeranmw.core.data.source.remote.response.AgentResponse
import retrofit2.http.GET

interface ApiService{
    @GET("agents?isPlayableCharacter=true")
    suspend fun getAgent(): AgentResponse
}