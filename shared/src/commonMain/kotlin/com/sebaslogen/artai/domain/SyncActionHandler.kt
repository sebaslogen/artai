package com.sebaslogen.artai.domain

import com.sebaslogen.artai.domain.models.Action


class SyncActionHandler(
    private val dynamicUIUseCase: DynamicUIUseCase,
    private val responseHandler: ResponseHandler,
) {
    suspend fun onAction(action: Action) {
        when (action) {
            is Action.OpenScreen -> dynamicUIUseCase.fetchScreenData(action.url, responseHandler)
            is Action.Unknown -> TODO("Not planning on implementing this error logging")
        }
    }
}