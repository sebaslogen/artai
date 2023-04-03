package com.sebaslogen.artai.di.components

import com.sebaslogen.artai.data.remote.api.DynamicUIApi
import com.sebaslogen.artai.di.scopes.Singleton
import com.sebaslogen.artai.networking.Http
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.builtin.CallResponseConverter
import de.jensklingenberg.ktorfit.ktorfit
import me.tatarka.inject.annotations.Provides

interface ApiServicesComponent {
    @Singleton
    @Provides
    fun ktorfit(http: Http): Ktorfit = ktorfit {
        httpClient(http)
        responseConverter(CallResponseConverter())
        baseUrl("https://raw.githubusercontent.com/")
    }

    @Singleton
    @Provides
    fun dynamicUIService(ktorfit: Ktorfit): DynamicUIApi = ktorfit.create()
}