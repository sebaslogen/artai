package com.sebaslogen.artai.data.remote.repositories

import com.sebaslogen.artai.di.scopes.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.tatarka.inject.annotations.Inject

@Singleton
@Inject
class FavoritesRepository {
    /**
     * Local cached list of favorites, the servers is always the ultimate source of truth
     */
    private val favoritesState: MutableStateFlow<List<String>> =
        MutableStateFlow(emptyList()) // TODO: Use here and in all other ids a value class: @JvmInline value class ArtId(val value: String)

    val favorites: StateFlow<List<String>> = favoritesState.asStateFlow()

    /**
     * Method to update the local (in memory) state of the favorites.
     * This should only be used to store server responses, not local updates.
     */
    internal fun updateCachedFavorites(favorites: List<String>) {
        favoritesState.value = favorites
    }

    /**
     * Method to update the favorites on the backend,
     * the response of that update should trigger the update of the local (in memory) state of the favorites
     */
    fun updateFavorites(favorites: List<String>) {
        // TODO do some real backend send before updating the local state
        favoritesState.value = favorites
    }
}
