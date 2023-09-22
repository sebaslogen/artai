package com.sebaslogen.artai.domain.components

import com.arkivanov.decompose.ComponentContext
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.sebaslogen.artai.domain.ActionHandler
import com.sebaslogen.artai.domain.ActionHandlerSync
import com.sebaslogen.artai.domain.Navigator
import com.sebaslogen.artai.domain.ResponseHandler
import com.sebaslogen.artai.domain.models.Action
import com.sebaslogen.artai.domain.models.DynamicUIDomainModel
import com.sebaslogen.artai.domain.models.Url
import com.sebaslogen.artai.domain.usecases.DynamicUIUseCase
import com.sebaslogen.artai.presentation.DynamicUIViewState
import com.sebaslogen.artai.utils.attachNewCoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import kotlin.coroutines.CoroutineContext

@Inject
class SDUIScreenComponent(
    @Assisted private val componentContext: ComponentContext,
    @Assisted private val coroutineContext: CoroutineContext,
    private val dynamicUIUseCase: DynamicUIUseCase,
    private val actionHandler: ActionHandlerSync,
    @Assisted private val navigator: Navigator,
    @Assisted val url: Url
) : ComponentContext by componentContext, ActionHandler {

    private val siblingComponents = mutableSetOf<ComponentContext>()
    private val viewModelScope = attachNewCoroutineScope(coroutineContext)

    private val mutableViewState = MutableStateFlow<DynamicUIViewState>(DynamicUIViewState.Loading)

    @NativeCoroutinesState
    val viewState: StateFlow<DynamicUIViewState> = mutableViewState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DynamicUIViewState.Loading
        )

    init {
        // TODO: Improve?
        if (url.value.isBlank()) {
            fetchHomeData()
        } else {
            fetchData { responseHandler ->
                dynamicUIUseCase.fetchScreenData(url = url.value, responseHandler = responseHandler)
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
        viewModelScope.launch {
            request(object : ResponseHandler {
                override fun handleSuccess(result: DynamicUIDomainModel) {
                    mutableViewState.value = mapDomainModelToUIState(result)
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

    /**
     * Create a new component using the [ComponentContext] of this object,
     * then add this new component to the list of siblings so it stays in memory as long as this object,
     * and finally return the new component to the caller to be used.
     */
    fun <T : ComponentContext> attachSiblingComponent(
        builder: (componentContext: ComponentContext, coroutineContext: CoroutineContext) -> T
    ): T {
        val newComponent = builder(componentContext, coroutineContext)
        siblingComponents.add(newComponent)
        return newComponent
    }

    override fun onAction(action: Action) {
        viewModelScope.launch {
            actionHandler.onAction(action = action, navigator = navigator)
        }
    }
}
