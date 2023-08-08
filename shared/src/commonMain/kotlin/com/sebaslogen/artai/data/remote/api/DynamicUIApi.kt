package com.sebaslogen.artai.data.remote.api

import com.sebaslogen.artai.data.remote.models.ApiCacheData
import com.sebaslogen.artai.data.remote.models.ApiCacheResponse
import com.sebaslogen.artai.data.remote.models.ApiScreenResponse
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.PUT
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Url

interface DynamicUIApi {

    @GET("sebaslogen/artai/main/fake-backend/home.json")
    suspend fun home(): Response<ApiScreenResponse>

    @GET("sebaslogen/artai/main/fake-backend/home-reloaded.json")
    suspend fun homeReloaded(): Response<ApiScreenResponse>

    @GET
    suspend fun screen(@Url fullPath: String): Response<ApiScreenResponse>

    @GET("{path}")
    suspend fun action(@Path("path") commandUrlPath: String): Response<ApiCacheResponse>
}