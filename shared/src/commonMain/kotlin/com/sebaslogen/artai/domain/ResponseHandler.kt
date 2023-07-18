package com.sebaslogen.artai.domain

import com.sebaslogen.artai.domain.models.DynamicUIDomainModel

interface ResponseHandler {
    fun handleSuccess(result: DynamicUIDomainModel)
}
