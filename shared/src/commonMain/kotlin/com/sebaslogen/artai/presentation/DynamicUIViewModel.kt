package com.sebaslogen.artai.presentation

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.sebaslogen.artai.domain.ActionHandler
import com.sebaslogen.artai.domain.ActionHandlerSync
import com.sebaslogen.artai.domain.DynamicUIUseCase
import com.sebaslogen.artai.domain.DynamicUIViewEvent
import com.sebaslogen.artai.domain.EventHandler
import com.sebaslogen.artai.domain.ResponseHandler
import com.sebaslogen.artai.domain.models.Action
import com.sebaslogen.artai.domain.models.DynamicUIDomainModel
import com.sebaslogen.artai.domain.models.Screen
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject


@Inject
open class DynamicUIViewModel(private val dynamicUIUseCase: DynamicUIUseCase) : KMMViewModel(), ActionHandler, EventHandler {

    private val _viewState = MutableStateFlow<DynamicUIViewState>(viewModelScope, DynamicUIViewState.Loading)
    private val _viewEvents = MutableStateFlow<DynamicUIViewEvent>(viewModelScope, DynamicUIViewEvent.OnAppStart)

    @NativeCoroutinesState
    val viewState: StateFlow<DynamicUIViewState> = _viewState.asStateFlow()

    @NativeCoroutinesState
    val viewEvents: StateFlow<DynamicUIViewEvent> = _viewEvents.asStateFlow()

    private val actionHandler = ActionHandlerSync(
        dynamicUIUseCase = dynamicUIUseCase,
        eventHandler = this
//        responseHandler = object : ResponseHandler { // TODO: Delete?
//            override fun handleSuccess(result: DynamicUIDomainModel) {
//                _viewState.value = mapDomainModelToUIState(result)
//            }
//        }
    )

    init {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        fetchData(dynamicUIUseCase::fetchHomeData)
    }

    private fun fetchFakeReloadData() {
        fetchData(dynamicUIUseCase::fetchHomeReloaded)
    }

    private fun fetchData(request: suspend (responseHandler: ResponseHandler) -> Unit) {
        viewModelScope.coroutineScope.launch {
            request(object : ResponseHandler {
                override fun handleSuccess(result: DynamicUIDomainModel) {
                    _viewState.value = mapDomainModelToUIState(result)
                }
            })
        }
    }

    private fun mapDomainModelToUIState(dynamicUIDomainModel: DynamicUIDomainModel) = when (dynamicUIDomainModel) {
        // TODO: Add nicer mappers from domain to UI state
        is DynamicUIDomainModel.Error -> DynamicUIViewState.Error(dynamicUIDomainModel.error)
        is DynamicUIDomainModel.Success -> DynamicUIViewState.Success(dynamicUIDomainModel.data)
    }

    fun onRefreshClicked() {
        fetchFakeReloadData()
    }

    override fun onAction(action: Action) {
        viewModelScope.coroutineScope.launch {
            actionHandler.onAction(action)
        }
    }

    override fun onEvent(event: DynamicUIViewEvent) {
        _viewEvents.value = event
    }
}

sealed class DynamicUIViewState {
    object Loading : DynamicUIViewState()
    data class Error(val error: Throwable) : DynamicUIViewState()
    data class Success(val data: Screen) : DynamicUIViewState()
}