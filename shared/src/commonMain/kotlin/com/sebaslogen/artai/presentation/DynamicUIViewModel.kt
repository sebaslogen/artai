package com.sebaslogen.artai.presentation

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.sebaslogen.artai.domain.ActionHandler
import com.sebaslogen.artai.domain.ActionHandlerSync
import com.sebaslogen.artai.domain.DynamicUINavigationState
import com.sebaslogen.artai.domain.DynamicUIUseCase
import com.sebaslogen.artai.domain.NavigationStateHandler
import com.sebaslogen.artai.domain.ResponseHandler
import com.sebaslogen.artai.domain.models.Action
import com.sebaslogen.artai.domain.models.DynamicUIDomainModel
import com.sebaslogen.artai.domain.models.Screen
import com.sebaslogen.artai.globalRepo
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject


@Inject
open class DynamicUIViewModel(
    private val navigationState: MutableStateFlow<DynamicUINavigationState>,
    private val dynamicUIUseCase: DynamicUIUseCase
) : KMMViewModel(), ActionHandler, NavigationStateHandler {

    private val _viewState = MutableStateFlow<DynamicUIViewState>(viewModelScope, DynamicUIViewState.Loading)

    @NativeCoroutinesState
    val viewState: StateFlow<DynamicUIViewState> = _viewState.asStateFlow()

    @Suppress("LeakingThis") // open modifier required for iOS/KMP
    private val actionHandler = ActionHandlerSync(
        dynamicUIUseCase = dynamicUIUseCase,
        navigationStateHandler = this
    )

    init {
        when (val initialNavigationState = navigationState.value) {
            DynamicUINavigationState.HomeScreen -> fetchHomeData()
            is DynamicUINavigationState.RemoteScreen -> fetchData { responseHandler ->
                dynamicUIUseCase.fetchScreenData(url = initialNavigationState.url, responseHandler = responseHandler)
            }
        }
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
//        fetchFakeReloadData()
        globalRepo?.let {repo ->
            viewModelScope.coroutineScope.launch {
                val message: String =
                    try {
                        repo.sduiRequest(screenId = "home")
                    } catch (exc: Exception) {
                        "Error: " + (exc.message ?: "Unknown error")
                    }
                Napier.w { "Sebas Message: $message" }
            }
        }
    }

    override fun onAction(action: Action) {
        viewModelScope.coroutineScope.launch {
            actionHandler.onAction(action)
        }
    }

    override fun onNavigationStateUpdate(event: DynamicUINavigationState) {
        navigationState.value = event
    }
}

sealed class DynamicUIViewState {
    object Loading : DynamicUIViewState()
    data class Error(val error: Throwable) : DynamicUIViewState()
    data class Success(val data: Screen) : DynamicUIViewState()
}