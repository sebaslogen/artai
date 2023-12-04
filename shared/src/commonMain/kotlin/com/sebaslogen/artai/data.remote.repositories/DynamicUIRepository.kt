package com.sebaslogen.artai.data.remote.repositories

import com.sebaslogen.artai.data.mappers.mapToSuccess
import com.sebaslogen.artai.data.remote.api.DynamicUIApi
import com.sebaslogen.artai.data.remote.models.ApiScreenResponse
import com.sebaslogen.artai.domain.models.DynamicUIDomainModel
import de.jensklingenberg.ktorfit.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject

@Inject
class DynamicUIRepository(private val dynamicUIApi: DynamicUIApi) {
    suspend fun home(): DynamicUIDomainModel = withContext(Dispatchers.IO) {
        val response = dynamicUIApi.home()
        mapResponseToDomain(response)
    }

    suspend fun homeReloaded(): DynamicUIDomainModel = withContext(Dispatchers.IO) {
        val response = dynamicUIApi.homeReloaded()
        mapResponseToDomain(response)
    }

    suspend fun screen(url: String): DynamicUIDomainModel = withContext(Dispatchers.IO) {
        val response = dynamicUIApi.screen(url)
        mapResponseToDomain(response)
    }

    private fun mapResponseToDomain(response: Response<ApiScreenResponse>): DynamicUIDomainModel {
        val apiHomeScreenResponseBody = response.body()
        return if (response.isSuccessful) {
            apiHomeScreenResponseBody?.let { it.mapToSuccess() } // Success
                ?: DynamicUIDomainModel.Error(Throwable("Error parsing response")) // Error parsing
        } else {
            val errorBody = response.errorBody()
            DynamicUIDomainModel.Error(Throwable("Error with status code: ${response.code} and error body:${errorBody}")) // Error retrieving
        }
    }
}

