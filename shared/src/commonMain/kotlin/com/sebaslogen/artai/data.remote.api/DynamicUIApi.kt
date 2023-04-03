package com.sebaslogen.artai.data.remote.api

import com.sebaslogen.artai.data.remote.models.ApiScreenResponse
import de.jensklingenberg.ktorfit.http.GET

interface DynamicUIApi {

    @GET("sebaslogen/artai/main/fake-backend/home.json")
    suspend fun home(): ApiScreenResponse
}