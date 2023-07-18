package com.sebaslogen.artai.domain

import com.sebaslogen.artai.domain.models.Action


class ActionHandlerSync(
    private val dynamicUIUseCase: DynamicUIUseCase,
    private val eventHandler: EventHandler
//    private val responseHandler: ResponseHandler, // TODO Delete?
) {
    suspend fun onAction(action: Action) {
        when (action) {
            is Action.OpenScreen -> eventHandler.onEvent(DynamicUIViewEvent.OnOpenScreen(action.url))
            // TODO This is on Present Screen ViewEvent dynamicUIUseCase.fetchScreenData(action.url, responseHandler)
            is Action.Unknown -> TODO("Not planning on implementing this error logging")
        }
    }
}