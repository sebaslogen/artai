package com.sebaslogen.artai.android.di.components

import android.content.Context
import com.arkivanov.decompose.DefaultComponentContext
import com.sebaslogen.artai.di.components.ApiServicesComponent
import com.sebaslogen.artai.di.components.NavigationComponent
import com.sebaslogen.artai.di.components.NetworkComponent
import com.sebaslogen.artai.di.scopes.Singleton
import com.sebaslogen.artai.domain.components.DefaultRootComponent
import com.sebaslogen.artai.domain.components.RootComponent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@Singleton
abstract class ApplicationComponent(
    @get:Provides val context: Context,
) : NetworkComponent, ApiServicesComponent, NavigationComponent, ImageLoaderComponent

interface ApplicationComponentProvider {
    val component: ApplicationComponent
}

val Context.applicationComponent get() = (applicationContext as ApplicationComponentProvider).component