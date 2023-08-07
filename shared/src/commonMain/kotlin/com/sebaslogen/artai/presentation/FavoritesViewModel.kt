package com.sebaslogen.artai.presentation

import com.sebaslogen.artai.domain.usecases.FavoritesUseCase
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
class FavoritesViewModel(
    private val favoritesUseCase: FavoritesUseCase
) {
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

    /**
     * Flip the favorited state of the given favorite [id].
     */
    fun toggleFavoriteState(id: String) = favoritesUseCase.toggleFavoriteState(id)
}