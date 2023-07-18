package com.sebaslogen.artai.domain

import com.sebaslogen.artai.data.remote.models.ApiAction
import kotlinx.coroutines.CoroutineScope

interface ActionHandler {
    fun onAction(action: ApiAction): Unit
}

class ActionHandleImplementation(
    private val coroutineScope: CoroutineScope,
    private val dynamicUIUseCase: DynamicUIUseCase,
    private val responseHandler: ResponseHandler,
) : ActionHandler {
    override fun onAction(action: ApiAction) {
        when (action) {
            is ApiAction.ApiOpenScreen -> dynamicUIUseCase.fetchScreenData(coroutineScope, action.url, responseHandler)
            is ApiAction.ApiUnknown -> TODO("Not planning on implementing this error logging")
        }
    }


}