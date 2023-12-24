package com.sebaslogen.artai.di.components

import com.sebaslogen.artai.di.scopes.ApplicationSingleton
import com.sebaslogen.artai.networking.Http
import com.sebaslogen.artai.networking.httpClient
import me.tatarka.inject.annotations.Provides

interface NetworkDIComponent {
    @ApplicationSingleton
    @Provides
    fun httpFactory(): Http = httpClient()
}