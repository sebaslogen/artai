package com.sebaslogen.artai.domain.components

import com.arkivanov.decompose.ComponentContext
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.sebaslogen.artai.domain.ActionHandler
import com.sebaslogen.artai.domain.ActionHandlerSync
import com.sebaslogen.artai.domain.Navigator
import com.sebaslogen.artai.domain.NotANavigatorHandler
import com.sebaslogen.artai.domain.ResponseHandler
import com.sebaslogen.artai.domain.models.Action
import com.sebaslogen.artai.domain.models.DynamicUIDomainModel
import com.sebaslogen.artai.domain.models.Favorite
import com.sebaslogen.artai.domain.models.Url
import com.sebaslogen.artai.domain.usecases.DynamicUIUseCase
import com.sebaslogen.artai.domain.usecases.FavoritesUseCase
import com.sebaslogen.artai.presentation.DynamicUIViewState
import com.sebaslogen.artai.presentation.DynamicUIViewStateReducer
import com.sebaslogen.artai.utils.attachNewCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import kotlin.coroutines.CoroutineContext

@Inject
class FavoriteComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted coroutineContext: CoroutineContext,
    private val favoritesUseCase: FavoritesUseCase,
    private val actionHandler: ActionHandlerSync,
) : ComponentContext by componentContext, ActionHandler {

    private val viewModelScope = attachNewCoroutineScope(coroutineContext)

    /**
     * Return a flow of favorited states (in the from of a boolean) for the given favorite id
     */
    @NativeCoroutines
    fun favoriteState(favorite: Favorite): Flow<Favorite> =
        favoritesUseCase.favoriteState(favorite.id).map { favorited ->
            favorite.copy(favorited = favorited)
        }

    override fun onAction(action: Action) {
        viewModelScope.launch {
            actionHandler.onAction(action = action, navigator = object : NotANavigatorHandler(name = "FavoritesViewModel") {})
        }
    }
}
