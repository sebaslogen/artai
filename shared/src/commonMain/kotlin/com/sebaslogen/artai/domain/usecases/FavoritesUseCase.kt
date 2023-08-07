package com.sebaslogen.artai.domain.usecases

import com.sebaslogen.artai.data.remote.repositories.FavoritesRepository
import me.tatarka.inject.annotations.Inject

@Inject
class FavoritesUseCase(
    private val favoritesRepository: FavoritesRepository
) {
    val favorites = favoritesRepository.favorites

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

    /**
     * Returns a function that can only update the favorited state of the given favorite [id].
     * This is called currying.
     */
    fun favoriteSetter(id: String): (Boolean) -> Unit {
        return fun(favorited: Boolean) {
            setFavorite(id, favorited)
        }
    }
}