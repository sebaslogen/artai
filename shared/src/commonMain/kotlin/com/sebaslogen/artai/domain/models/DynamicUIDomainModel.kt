package com.sebaslogen.artai.domain.models


sealed class DynamicUIDomainModel {
    data class Error(val error: Throwable) : DynamicUIDomainModel() // TODO: Add domain error models
    data class Success(val data: Screen) : DynamicUIDomainModel() // TODO: Map to domain model
}