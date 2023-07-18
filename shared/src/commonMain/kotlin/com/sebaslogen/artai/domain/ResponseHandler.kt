package com.sebaslogen.artai.domain

import com.sebaslogen.artai.data.remote.repositories.DynamicUIDomainModel

interface ResponseHandler {
    fun handleSuccess(result: DynamicUIDomainModel)
}
