package com.pangeranmw.expertvalorant.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.pangeranmw.core.domain.usecase.ValorantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val valorantUseCase: ValorantUseCase) : ViewModel() {
    val queryAgent = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchResult = queryAgent
        .debounce(300)
        .distinctUntilChanged()
        .mapLatest {
            valorantUseCase.getAgent("%$it%")
        }
        .asLiveData()

    val agent = valorantUseCase.getAllAgent().asLiveData()
}