package com.sebaslogen.artai.domain

import com.sebaslogen.artai.data.remote.repositories.DynamicUIDomainModel
import com.sebaslogen.artai.data.remote.repositories.DynamicUIRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DynamicUIUseCase(
    private val dynamicUIRepository: DynamicUIRepository
) {

    fun fetchScreenData(coroutineScope: CoroutineScope, url: String, responseHandler: ResponseHandler) {
        coroutineScope.fetchData(responseHandler) { dynamicUIRepository.screen(url) }
    }

    private fun CoroutineScope.fetchData(responseHandler: ResponseHandler, request: suspend () -> DynamicUIDomainModel) {
        launch {
            val dynamicUIDomainModel: DynamicUIDomainModel = request()
            responseHandler.handleSuccess(dynamicUIDomainModel)
        }
    }
}