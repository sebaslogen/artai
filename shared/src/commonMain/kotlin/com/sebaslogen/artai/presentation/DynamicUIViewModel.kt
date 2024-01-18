package com.sebaslogen.artai.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import app.cash.molecule.moleculeFlow
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmm.viewmodel.stateIn
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.sebaslogen.artai.domain.ActionHandler
import com.sebaslogen.artai.domain.ActionHandlerSync
import com.sebaslogen.artai.domain.DynamicUINavigationState
import com.sebaslogen.artai.domain.NavigationStateHandler
import com.sebaslogen.artai.domain.ResponseHandler
import com.sebaslogen.artai.domain.models.Action
import com.sebaslogen.artai.domain.models.DynamicUIDomainModel
import com.sebaslogen.artai.domain.usecases.DynamicUIUseCase
import com.sebaslogen.artai.domain.usecases.FavoritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import com.sebaslogen.artai.domain.models.CarouselItem
import com.sebaslogen.artai.domain.models.Screen
import com.sebaslogen.artai.domain.models.Section
import io.github.aakira.napier.Napier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform


@Inject
open class DynamicUIViewModel(
    private val navigationState: MutableStateFlow<DynamicUINavigationState>,
    private val dynamicUIUseCase: DynamicUIUseCase,
//    private val favoritesUseCase: FavoritesUseCase,
    private val actionHandler: ActionHandlerSync
) : KMMViewModel(), ActionHandler, NavigationStateHandler {

    private val _viewState = MutableStateFlow<DynamicUIViewState>(viewModelScope, DynamicUIViewState.Loading)

    val viewModelsProvider = ViewModelsProvider() // TODO()

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val viewState: StateFlow<DynamicUIViewState> = _viewState
        .onEach {
            Napier.d { "Before reducer we get $it" }
        }
//        .combine(favoritesUseCase.favorites) { state, favorites ->
//            DynamicUIViewStateReducer.reduce(state, favorites)
//        }
//        .transformLatest<DynamicUIViewState, DynamicUIViewState> {
        .flatMapLatest { viewState ->
            moleculeFlow(mode = RecompositionMode.Immediate) {
                reduceScreenState(viewState, viewModelsProvider)
            }
        }
        .onEach {
            Napier.d { "After reducer we get $it" }
        }
        .stateIn(
            viewModelScope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DynamicUIViewState.Loading
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
        fetchFakeReloadData()
    }

    override fun onAction(action: Action) {
        viewModelScope.coroutineScope.launch {
            actionHandler.onAction(action = action, navigationStateHandler = this@DynamicUIViewModel)
        }
    }

    override fun onNavigationStateUpdate(event: DynamicUINavigationState) {
        navigationState.value = event
    }
}

