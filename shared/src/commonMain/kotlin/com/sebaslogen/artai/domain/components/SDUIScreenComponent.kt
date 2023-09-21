package com.sebaslogen.artai.domain.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.sebaslogen.artai.domain.ActionHandler
import com.sebaslogen.artai.domain.ActionHandlerSync
import com.sebaslogen.artai.domain.Navigator
import com.sebaslogen.artai.domain.ResponseHandler
import com.sebaslogen.artai.domain.models.Action
import com.sebaslogen.artai.domain.models.DynamicUIDomainModel
import com.sebaslogen.artai.domain.models.Url
import com.sebaslogen.artai.domain.usecases.DynamicUIUseCase
import com.sebaslogen.artai.domain.usecases.FavoritesUseCase
import com.sebaslogen.artai.presentation.DynamicUIViewState
import com.sebaslogen.artai.presentation.viewmodels.SDUIScreenComponentViewModel
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import kotlin.coroutines.CoroutineContext

@Inject
class SDUIScreenComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted private val coroutineContext: CoroutineContext,
    private val dynamicUIUseCase: DynamicUIUseCase,
    private val favoritesUseCase: FavoritesUseCase,
    private val actionHandler: ActionHandlerSync,
    @Assisted private val navigator: Navigator,
    @Assisted val url: Url
) : ComponentContext by componentContext, ActionHandler {

    private val viewModel = instanceKeeper.getOrCreate {
        SDUIScreenComponentViewModel(
            coroutineContext = coroutineContext,
            favoritesUseCase = favoritesUseCase
        )
    }

    private val viewModelScope = viewModel.viewModelScope

    private val mutableViewState = viewModel.mutableViewState
    val viewState = viewModel.viewState

    init {
        // TODO: Improve?
        if (url.value.isBlank()) {
            fetchHomeData()
        } else {
            fetchData { responseHandler ->
                dynamicUIUseCase.fetchScreenData(url = url.value, responseHandler = responseHandler)
            }
        }
        lifecycle.doOnDestroy {
            // TODO: Move ViewModel to this class and cancel coroutine here
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

    override fun onAction(action: Action) {
        viewModelScope.launch {
            actionHandler.onAction(action = action, navigator = navigator)
        }
    }
}
