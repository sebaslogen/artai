package com.sebaslogen.artai.presentation

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.sebaslogen.artai.domain.ActionHandler
import com.sebaslogen.artai.domain.ActionHandlerSync
import com.sebaslogen.artai.domain.DynamicUINavigationState
import com.sebaslogen.artai.domain.NavigationStateHandler
import com.sebaslogen.artai.domain.NotANavigationStateHandler
import com.sebaslogen.artai.domain.models.Action
import com.sebaslogen.artai.domain.models.Favorite
import com.sebaslogen.artai.domain.usecases.FavoritesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
open class FavoritesViewModel(
    private val favoritesUseCase: FavoritesUseCase,
    private val actionHandler: ActionHandlerSync
) : KMMViewModel(), ActionHandler {
    /**
     * Return a flow of favorited states (in the from of a boolean) for the given favorite id
     */
    fun favoriteState(favorite: Favorite): Flow<Favorite> =
        favoritesUseCase.favoriteState(favorite.id).map { favorited ->
            favorite.copy(favorited = favorited)
        }

    //    val newFavState = favorites.contains(id)
    //    return if (newFavState == favorited) this else this.copy(favorited = favorites.contains(id))

    override fun onAction(action: Action) {
        viewModelScope.coroutineScope.launch {
            actionHandler.onAction(action = action, navigationStateHandler = object : NotANavigationStateHandler(name = "FavoritesViewModel") {})
        }
    }

    // TODO: Remove or use this nice idea
//    /**
//     * Returns a function that can only update the favorited state of the given favorite [id].
//     * This is called currying.
//     */
//    fun favoriteSetter(id: String): (Boolean) -> Unit {
//        return favoritesUseCase.favoriteSetter(id)
//    }

    // TODO: Remove or use this nice idea
//    /**
//     * Flip the favorited state of the given favorite [id].
//     */
//    suspend fun toggleFavoriteState(id: String) = favoritesUseCase.toggleFavoriteState(id, action.url)
}