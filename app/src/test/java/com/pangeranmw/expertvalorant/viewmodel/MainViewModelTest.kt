package com.pangeranmw.expertvalorant.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pangeranmw.core.data.Resource
import com.pangeranmw.core.domain.model.Agent
import com.pangeranmw.core.domain.usecase.ValorantUseCase
import com.pangeranmw.expertvalorant.home.MainViewModel
import com.pangeranmw.expertvalorant.util.DataDummy
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import com.pangeranmw.expertvalorant.util.MainDispatcherRule
import com.pangeranmw.expertvalorant.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var valorantUseCase: ValorantUseCase
    private lateinit var mainViewModel: MainViewModel
    private val dummyAgent = DataDummy.generateAgent()

    @Test
    fun `when Get Agent Should Not Null and Return Success`() = runTest {
        val expectedAgent = Resource.Success(dummyAgent)
        `when`(valorantUseCase.getAllAgent()).thenReturn(flowOf(expectedAgent))
        mainViewModel = MainViewModel(valorantUseCase)
        val actualAgent = mainViewModel.agent.getOrAwaitValue()

        verify(valorantUseCase).getAllAgent()
        assertNotNull(actualAgent)
        assertTrue(actualAgent is Resource.Success)
        assertEquals(dummyAgent.size, (actualAgent as Resource.Success).data?.size)
    }

    @Test
    fun `when Network Error Should Return Error`() = runTest {
        val expectedAgent = Resource.Error<List<Agent>>("Error")
        `when`(valorantUseCase.getAllAgent()).thenReturn(flowOf(expectedAgent))
        mainViewModel = MainViewModel(valorantUseCase)
        val actualAgent = mainViewModel.agent.getOrAwaitValue()
        verify(valorantUseCase).getAllAgent()
        assertNotNull(actualAgent)
        assertTrue(actualAgent is Resource.Error)
    }

    @Test
    fun `when Search Agent Should Not Null`() = runTest {
        val expectedAgent = dummyAgent
        val queryAgent = "senti"

        `when`(valorantUseCase.getAgent(queryAgent)).thenReturn(flowOf(expectedAgent.filter{it.name.contains(queryAgent) || it.roleFilter.contains(queryAgent)}))
        mainViewModel = MainViewModel(valorantUseCase)
        mainViewModel.queryAgent.value = queryAgent
        val actualAgent = mainViewModel.searchResult.getOrAwaitValue()

        verify(valorantUseCase).getAgent(queryAgent)
        assertNotNull(actualAgent)
        assertTrue(actualAgent.first()[0].name.contains(queryAgent) || actualAgent.first()[0].roleFilter.contains(queryAgent))
        assertEquals(dummyAgent.size, actualAgent.first().size)
    }

}