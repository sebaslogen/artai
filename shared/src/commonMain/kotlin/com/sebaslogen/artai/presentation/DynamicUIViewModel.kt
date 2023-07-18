package com.sebaslogen.artai.presentation

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.sebaslogen.artai.data.remote.models.ApiAction
import com.sebaslogen.artai.data.remote.models.ApiScreenResponse
import com.sebaslogen.artai.data.remote.repositories.DynamicUIDomainModel
import com.sebaslogen.artai.data.remote.repositories.DynamicUIRepository
import com.sebaslogen.artai.domain.ActionHandleImplementation
import com.sebaslogen.artai.domain.ActionHandler
import com.sebaslogen.artai.domain.DynamicUIUseCase
import com.sebaslogen.artai.domain.ResponseHandler
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject


@Inject
open class DynamicUIViewModel(private val dynamicUIRepository: DynamicUIRepository) : KMMViewModel(), ActionHandler {

    private val _viewState = MutableStateFlow<DynamicUIViewState>(viewModelScope, DynamicUIViewState.Loading)

    @NativeCoroutinesState
    val viewState: StateFlow<DynamicUIViewState> = _viewState.asStateFlow()

    private val actionHandler: ActionHandler = ActionHandleImplementation(
        coroutineScope = viewModelScope.coroutineScope,
        dynamicUIUseCase = DynamicUIUseCase(dynamicUIRepository = dynamicUIRepository), // TODO: Inject this
        responseHandler = object : ResponseHandler {
            override fun handleSuccess(result: DynamicUIDomainModel) {
                _viewState.value = mapDomainModelToUIState(result)
            }
        }
    )

    init {
        fetchData()
    }

    private fun fetchData() {
        fetchData { dynamicUIRepository.home() }
    }

    private fun fetchFakeReloadData() {
        fetchData { dynamicUIRepository.homeReloaded() }
    }

    private fun fetchData(request: suspend () -> DynamicUIDomainModel) {
        viewModelScope.coroutineScope.launch {
            val dynamicUIDomainModel: DynamicUIDomainModel = request()
            _viewState.value = mapDomainModelToUIState(dynamicUIDomainModel)
        }
    }

    private fun mapDomainModelToUIState(dynamicUIDomainModel: DynamicUIDomainModel) = when (dynamicUIDomainModel) {
        // TODO: Add nicer mappers from domain to UI state
        is DynamicUIDomainModel.Error -> DynamicUIViewState.Error(dynamicUIDomainModel.error)
        is DynamicUIDomainModel.Success -> DynamicUIViewState.Success(dynamicUIDomainModel.data)
    }

    @NativeCoroutines
    suspend fun getHome(): ApiScreenResponse = when (val uiState = mapDomainModelToUIState(dynamicUIRepository.home())) {
        is DynamicUIViewState.Error, DynamicUIViewState.Loading -> TODO("Not planning on implementing this experimental function getHome()")
        is DynamicUIViewState.Success -> uiState.data
    }

    fun onRefreshClicked() {
        fetchFakeReloadData()
    }

    override fun onAction(action: ApiAction) {
        actionHandler.onAction(action)
    }
}

sealed class DynamicUIViewState {
    object Loading : DynamicUIViewState()
    data class Error(val error: Throwable) : DynamicUIViewState()
    data class Success(val data: ApiScreenResponse) : DynamicUIViewState()
}