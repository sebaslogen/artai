package com.sebaslogen.artai.domain

import com.sebaslogen.artai.domain.models.Action
import com.sebaslogen.artai.domain.usecases.DynamicUIUseCase
import com.sebaslogen.artai.domain.usecases.FavoritesUseCase


class ActionHandlerSync(
    private val dynamicUIUseCase: DynamicUIUseCase,
    private val favoritesUseCase: FavoritesUseCase,
    private val navigationStateHandler: NavigationStateHandler
) {
    fun onAction(action: Action) {
        when (action) {
            is Action.OpenScreen -> navigationStateHandler.onNavigationStateUpdate(DynamicUINavigationState.RemoteScreen(action.url))
            // TODO This is on Present Screen ViewEvent dynamicUIUseCase.fetchScreenData(action.url, responseHandler)
            is Action.ToggleFavoriteState -> favoritesUseCase.toggleFavoriteState(action.id)
            is Action.Unknown -> TODO("Not planning on implementing this error logging")
        }
    }
}