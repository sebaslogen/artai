package com.sebaslogen.artai.di.components

import com.sebaslogen.artai.data.remote.api.DynamicUIApi
import com.sebaslogen.artai.data.remote.api.createDynamicUIApi
import com.sebaslogen.artai.di.scopes.ApplicationSingleton
import com.sebaslogen.artai.networking.Http
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.ResponseConverterFactory
import de.jensklingenberg.ktorfit.ktorfit
import me.tatarka.inject.annotations.Provides

interface ApiServicesDIComponent {
    @ApplicationSingleton
    @Provides
    fun ktorfit(http: Http): Ktorfit = ktorfit {
        httpClient(http)
        converterFactories(ResponseConverterFactory())
        baseUrl("https://raw.githubusercontent.com/")
    }

    @ApplicationSingleton
    @Provides
    fun dynamicUIService(ktorfit: Ktorfit): DynamicUIApi = ktorfit.createDynamicUIApi()
}