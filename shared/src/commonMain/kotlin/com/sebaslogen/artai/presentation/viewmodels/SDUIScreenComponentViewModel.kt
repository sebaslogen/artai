package com.sebaslogen.artai.presentation.viewmodels

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.sebaslogen.artai.domain.usecases.FavoritesUseCase
import com.sebaslogen.artai.presentation.DynamicUIViewState
import com.sebaslogen.artai.presentation.DynamicUIViewStateReducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.CoroutineContext

/**
 * The ViewModel only has two responsibilities:
 * - Handle and expose the [CoroutineScope] with the [viewModelScope] field. The scope will be used by the component to make async calls
 * - Store and keep in memory the two versions of the view state in [mutableViewState] and [viewState]. The view state will be updated and exposed via de component
 */
class SDUIScreenComponentViewModel(
    coroutineContext: CoroutineContext,
    favoritesUseCase: FavoritesUseCase,
) : InstanceKeeper.Instance {

    val viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())

    val mutableViewState = MutableStateFlow<DynamicUIViewState>(DynamicUIViewState.Loading)

    private var initialized: Boolean = false

    @NativeCoroutinesState
    val viewState: StateFlow<DynamicUIViewState> = mutableViewState
        .combine(favoritesUseCase.favorites) { state, favorites ->
            DynamicUIViewStateReducer.reduce(state, favorites)
        } // TODO: Move reducer to separate FavsVM
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DynamicUIViewState.Loading
        )

    override fun onDestroy() {
        viewModelScope.cancel()
    }

    fun shouldInitialize(): Boolean {
        val shouldInitialize = !initialized
        initialized = true
        return shouldInitialize
    }
}