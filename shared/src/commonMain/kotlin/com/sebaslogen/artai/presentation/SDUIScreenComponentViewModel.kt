package com.sebaslogen.artai.presentation

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.sebaslogen.artai.domain.ActionHandler
import com.sebaslogen.artai.domain.ActionHandlerSync
import com.sebaslogen.artai.domain.Navigator
import com.sebaslogen.artai.domain.ResponseHandler
import com.sebaslogen.artai.domain.models.Action
import com.sebaslogen.artai.domain.models.DynamicUIDomainModel
import com.sebaslogen.artai.domain.models.Url
import com.sebaslogen.artai.domain.usecases.DynamicUIUseCase
import com.sebaslogen.artai.domain.usecases.FavoritesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import kotlin.coroutines.CoroutineContext

@Inject
class SDUIScreenComponentViewModel(
    @Assisted
    coroutineContext: CoroutineContext,
    private val dynamicUIUseCase: DynamicUIUseCase,
    private val favoritesUseCase: FavoritesUseCase,
    private val actionHandler: ActionHandlerSync,
    @Assisted
    var navigator: Navigator,
    @Assisted
    private val url: Url
) : InstanceKeeper.Instance, ActionHandler {

    private val viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())

    private val _viewState = MutableStateFlow<DynamicUIViewState>(DynamicUIViewState.Loading)

    @NativeCoroutinesState
    val viewState: StateFlow<DynamicUIViewState> = _viewState
        .combine(favoritesUseCase.favorites) { state, favorites ->
            DynamicUIViewStateReducer.reduce(state, favorites)
        } // TODO: Move reducer to separate FavsVM
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
        viewModelScope.launch {
            actionHandler.onAction(action = action, navigator = navigator)
        }
    }

    override fun onDestroy() {
        viewModelScope.cancel()
    }
}