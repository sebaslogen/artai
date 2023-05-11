package com.sebaslogen.artai.domain

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.sebaslogen.artai.data.remote.models.ApiScreenResponse
import com.sebaslogen.artai.data.remote.repositories.DynamicUIRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
open class DynamicUIViewModel(private val dynamicUIRepository: DynamicUIRepository) : KMMViewModel() {

    private val _viewState = MutableStateFlow<DynamicUIViewState>(viewModelScope, DynamicUIViewState.Loading)

    @NativeCoroutinesState
    val viewState: StateFlow<DynamicUIViewState> = _viewState.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.coroutineScope.launch {
            val home = dynamicUIRepository.home()
            _viewState.value = DynamicUIViewState.Success(home)
        }
    }

    private fun fetchFakeReloadData() {
        viewModelScope.coroutineScope.launch {
            val home = dynamicUIRepository.homeReloaded()
            _viewState.value = DynamicUIViewState.Success(home)
        }
    }

    @NativeCoroutines
    suspend fun getHome() = dynamicUIRepository.home()

    fun onRefreshClicked() {
        fetchFakeReloadData()
    }
}

sealed class DynamicUIViewState {
    object Loading : DynamicUIViewState()
    data class Error(val error: Throwable) : DynamicUIViewState()
    data class Success(val data: ApiScreenResponse) : DynamicUIViewState()
}