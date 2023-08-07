package com.sebaslogen.artai.domain.usecases

import com.sebaslogen.artai.data.remote.repositories.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class FavoritesUseCase(
    private val favoritesRepository: FavoritesRepository
) {
    private val favorites = favoritesRepository.favorites

    /**
     * Return a flow of favorited states (in the from of a boolean) for the given favorite id
     */
    fun favoriteState(id: String): Flow<Boolean> = favorites.map { it.contains(id) }

    private fun setFavorite(id: String, favorited: Boolean) {
        val favorites = favoritesRepository.favorites.value
        val alreadyFavorited = favorites.contains(id)
        val addFavorite = favorited && !alreadyFavorited
        val removeFavorite = !favorited && alreadyFavorited
        if (addFavorite) {
            favoritesRepository.updateFavorites(favorites + id)
        } else if (removeFavorite) {
            favoritesRepository.updateFavorites(favorites - id)
        }
    }

    // TODO: Remove or use this nice idea
//    /**
//     * Returns a function that can only update the favorited state of the given favorite [id].
//     * This is called currying.
//     */
//    fun favoriteSetter(id: String): (Boolean) -> Unit {
//        return fun(favorited: Boolean) {
//            setFavorite(id, favorited)
//        }
//    }

    /**
     * Flip the favorited state of the given favorite [id].
     */
    fun toggleFavoriteState(id: String) {
        val favorites = favoritesRepository.favorites.value
        val favorited = favorites.contains(id)
        val toggledFavorited = !favorited
        setFavorite(id, toggledFavorited)
    }
}