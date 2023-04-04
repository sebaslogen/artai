package com.sebaslogen.artai.domain

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmm.viewmodel.stateIn
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.sebaslogen.artai.data.remote.models.ApiScreenResponse
import com.sebaslogen.artai.data.remote.repositories.DynamicUIRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
open class DynamicUIViewModel(private val dynamicUIRepository: DynamicUIRepository) : KMMViewModel() {

    private val _viewState = MutableStateFlow<DynamicUIViewState>(viewModelScope, DynamicUIViewState.Loading)

    @NativeCoroutinesState
//    val viewState: StateFlow<DynamicUIViewState> = _viewState.asStateFlow() // TODO: Choose which flow alternative to use
    val viewState: StateFlow<DynamicUIViewState> = _viewState.stateIn(
        viewModelScope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DynamicUIViewState.Loading
    )

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.coroutineScope.launch {
            val home = dynamicUIRepository.home()
            _viewState.value = DynamicUIViewState.Success(home)
        }
    }

    @NativeCoroutines
    suspend fun getHome() = dynamicUIRepository.home()

    fun onRefreshClicked() {
        fetchData()
    }
}

sealed class DynamicUIViewState {
    object Loading : DynamicUIViewState()
    data class Error(val error: Throwable) : DynamicUIViewState()
    data class Success(val data: ApiScreenResponse) : DynamicUIViewState()
}