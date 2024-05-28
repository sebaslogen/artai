package com.sebaslogen.artai.data.remote.repositories

import com.sebaslogen.artai.data.mappers.mapToCacheData
import com.sebaslogen.artai.data.mappers.mapToSuccess
import com.sebaslogen.artai.data.remote.api.DynamicUIApi
import com.sebaslogen.artai.data.remote.models.ApiScreenResponse
import com.sebaslogen.artai.di.scopes.ApplicationSingleton
import com.sebaslogen.artai.domain.models.CacheData
import com.sebaslogen.artai.domain.models.DynamicUIDomainModel
import de.jensklingenberg.ktorfit.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject

@ApplicationSingleton
@Inject
class DynamicUIRepository(
    private val dynamicUIApi: DynamicUIApi,
    private val favoritesRepository: FavoritesRepository
) {

    suspend fun home(): DynamicUIDomainModel = withContext(Dispatchers.IO) {
        val response = dynamicUIApi.home()
        getDomainModelResponseAndUpdateCache(response)
    }

    suspend fun homeReloaded(): DynamicUIDomainModel = withContext(Dispatchers.IO) {
        val response = dynamicUIApi.homeReloaded()
        getDomainModelResponseAndUpdateCache(response)
    }

    suspend fun screen(url: String): DynamicUIDomainModel = withContext(Dispatchers.IO) {
        val response = dynamicUIApi.screen(url)
        getDomainModelResponseAndUpdateCache(response)
    }

    private fun getDomainModelResponseAndUpdateCache(response: Response<ApiScreenResponse>): DynamicUIDomainModel {
        val (domainModel, cacheData) = mapResponseToDomain(response)
        updateCache(cacheData)
        return domainModel
    }

    private fun mapResponseToDomain(response: Response<ApiScreenResponse>): Pair<DynamicUIDomainModel, CacheData?> {
        val apiHomeScreenResponseBody: ApiScreenResponse? = response.body()
        val cacheData: CacheData? = // Only parse and update cached when the response was successful
            if (response.isSuccessful) apiHomeScreenResponseBody?.mapToCacheData() else null

        val domainModel: DynamicUIDomainModel = if (response.isSuccessful) {
            val favorites = cacheData?.favorites ?: favoritesRepository.favorites.value // Use the latest data from the JSON response or the cached data
            apiHomeScreenResponseBody?.mapToSuccess(favorites) // Success
                ?: DynamicUIDomainModel.Error(Throwable("Error parsing response")) // Error parsing
        } else {
            val errorBody = response.errorBody()
            DynamicUIDomainModel.Error(Throwable("Error with status code: ${response.code} and error body:${errorBody}")) // Error retrieving

        }

        return domainModel to cacheData
    }

    private fun updateCache(cacheData: CacheData?) {
        if (cacheData == null) return
        cacheData.favorites?.let { favoritesRepository.updateCachedFavorites(it) }
    }
}


