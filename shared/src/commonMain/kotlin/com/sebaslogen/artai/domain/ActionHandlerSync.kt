package com.sebaslogen.artai.domain

import com.sebaslogen.artai.domain.models.Action


class ActionHandlerSync(
    private val dynamicUIUseCase: DynamicUIUseCase,
    private val navigationStateHandler: NavigationStateHandler
) {
    suspend fun onAction(action: Action) {
        when (action) {
            is Action.OpenScreen -> navigationStateHandler.onNavigationStateUpdate(DynamicUINavigationState.RemoteScreen(action.url))
            // TODO This is on Present Screen ViewEvent dynamicUIUseCase.fetchScreenData(action.url, responseHandler)
            is Action.Unknown -> TODO("Not planning on implementing this error logging")
        }
    }
}