package com.sebaslogen.artai.data.remote.repositories

import com.sebaslogen.artai.data.remote.api.DynamicUIApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject

@Inject
class DynamicUIRepository(private val dynamicUIApi: DynamicUIApi) {
    suspend fun home() = withContext(Dispatchers.IO) {
        dynamicUIApi.home() // TODO: Map to domain model
        // TODO: Add error handling to return success or error
    }

    suspend fun homeReloaded() = withContext(Dispatchers.IO) {
        dynamicUIApi.homeReloaded() // TODO: Map to domain model
        // TODO: Add error handling to return success or error
    }
}