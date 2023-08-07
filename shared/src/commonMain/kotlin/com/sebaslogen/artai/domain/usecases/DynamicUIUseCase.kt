package com.sebaslogen.artai.domain.usecases

import com.sebaslogen.artai.data.remote.repositories.DynamicUIRepository
import com.sebaslogen.artai.domain.ResponseHandler
import com.sebaslogen.artai.domain.models.DynamicUIDomainModel
import me.tatarka.inject.annotations.Inject

@Inject
class DynamicUIUseCase(
    private val dynamicUIRepository: DynamicUIRepository
) {

    suspend fun fetchScreenData(url: String, responseHandler: ResponseHandler) {
        fetchData(responseHandler) { dynamicUIRepository.screen(url) }
    }

    suspend fun fetchHomeData(responseHandler: ResponseHandler) {
        fetchData(responseHandler) { dynamicUIRepository.home() }
    }

    suspend fun fetchHomeReloaded(responseHandler: ResponseHandler) {
        fetchData(responseHandler) { dynamicUIRepository.homeReloaded() }
    }

    private suspend fun fetchData(responseHandler: ResponseHandler, request: suspend () -> DynamicUIDomainModel) {
        val dynamicUIDomainModel: DynamicUIDomainModel = request()
        responseHandler.handleSuccess(dynamicUIDomainModel)
    }
}