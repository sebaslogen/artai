package com.sebaslogen.artai.presentation

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmm.viewmodel.stateIn
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.sebaslogen.artai.domain.ActionHandler
import com.sebaslogen.artai.domain.ActionHandlerSync
import com.sebaslogen.artai.domain.ResponseHandler
import com.sebaslogen.artai.domain.models.Action
import com.sebaslogen.artai.domain.models.DynamicUIDomainModel
import com.sebaslogen.artai.domain.usecases.DynamicUIUseCase
import com.sebaslogen.artai.domain.usecases.FavoritesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject


//@Inject
open class DynamicUIViewModel(
    private val dynamicUIUseCase: DynamicUIUseCase,
    private val favoritesUseCase: FavoritesUseCase,
    private val actionHandler: ActionHandlerSync
) : KMMViewModel(), ActionHandler {

    // TODO: Move all this to SDUIScreenComponent

    private val _viewState = MutableStateFlow<DynamicUIViewState>(viewModelScope, DynamicUIViewState.Loading)

    @NativeCoroutinesState
    val viewState: StateFlow<DynamicUIViewState> = _viewState
        .combine(favoritesUseCase.favorites) { state, favorites ->
            DynamicUIViewStateReducer.reduce(state, favorites)
        }
        .stateIn(
            viewModelScope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DynamicUIViewState.Loading
        )

    init {
        fetchHomeData()
    }
    // TODO
//        when (val initialNavigationState = navigationState.value) {
//            DynamicUINavigationState.HomeScreen -> fetchHomeData()
//            is DynamicUINavigationState.RemoteScreen -> fetchData { responseHandler ->
//                dynamicUIUseCase.fetchScreenData(url = initialNavigationState.url, responseHandler = responseHandler)
//            }
//        }
//    }

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
            actionHandler.onAction(action = action)
        }
    }
}

