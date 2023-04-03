package com.sebaslogen.artai.domain

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.sebaslogen.artai.data.remote.models.ApiScreenResponse
import com.sebaslogen.artai.data.remote.repositories.DynamicUIRepository
import me.tatarka.inject.annotations.Inject

@Inject
open class DynamicUIViewModel(private val dynamicUIRepository: DynamicUIRepository) : KMMViewModel() {

    // TODO: Add flows for view state
//    private val _viewState = MutableStateFlow<DynamicUIViewState>(viewModelScope, DynamicUIViewState.Loading)
//
//    @NativeCoroutinesState
//    val viewState: StateFlow<DynamicUIViewState> = _viewState.asStateFlow()

    @NativeCoroutines
    suspend fun getHome() = dynamicUIRepository.home()


}

sealed interface DynamicUIViewState {
    object Loading : DynamicUIViewState
    data class Error(val error: Throwable) : DynamicUIViewState
    data class Success(val data: ApiScreenResponse) : DynamicUIViewState
}