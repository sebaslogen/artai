package com.sebaslogen.artai.presentation

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.sebaslogen.artai.domain.usecases.FavoritesUseCase
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
class FavoritesViewModel(
    private val favoritesUseCase: FavoritesUseCase
): KMMViewModel() {
    /**
     * Return a flow of favorited states (in the from of a boolean) for the given favorite id
     */
    fun favoriteState(id: String): Flow<Boolean> = favoritesUseCase.favoriteState(id)

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