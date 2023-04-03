package com.sebaslogen.artai.data.remote.repositories

import com.sebaslogen.artai.data.remote.api.DynamicUIApi
import me.tatarka.inject.annotations.Inject

@Inject
class DynamicUIRepository(private val dynamicUIApi: DynamicUIApi) {
    suspend fun home() = dynamicUIApi.home() // TODO: Map to domain model
}