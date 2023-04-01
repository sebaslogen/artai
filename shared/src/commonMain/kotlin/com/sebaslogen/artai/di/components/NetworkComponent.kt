package com.sebaslogen.artai.di.components

import com.sebaslogen.artai.di.scopes.Singleton
import com.sebaslogen.artai.networking.httpClient
import me.tatarka.inject.annotations.Provides

interface NetworkComponent {
    @Singleton
    @Provides
    fun httpFactory() = httpClient()
}