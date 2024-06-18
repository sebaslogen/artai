package com.sebaslogen.artai.domain

import com.sebaslogen.artai.domain.models.Action
import com.sebaslogen.artai.domain.usecases.DynamicUIUseCase
import com.sebaslogen.artai.domain.usecases.FavoritesUseCase
import me.tatarka.inject.annotations.Inject

@Inject
class ActionHandlerSync(
    private val dynamicUIUseCase: DynamicUIUseCase,
    private val favoritesUseCase: FavoritesUseCase,
) {
    suspend fun onAction(action: Action, navigator: Navigator) {
        when (action) {
            is Action.OpenScreen -> navigator.onNavigation(action)
            // TODO This is on Present Screen ViewEvent dynamicUIUseCase.fetchScreenData(action.url, responseHandler)
            is Action.ToggleFavoriteState -> favoritesUseCase.toggleFavoriteState(action.id, action.url)
            is Action.Unknown -> TODO("Not planning on implementing this error logging")
        }
    }
}