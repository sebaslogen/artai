package com.sebaslogen.artai.android

import com.arkivanov.decompose.ComponentContext
import com.sebaslogen.artai.android.di.components.AndroidApplicationDIComponent
import com.sebaslogen.artai.data.remote.repositories.DynamicUIRepository
import com.sebaslogen.artai.di.scopes.MainActivityScope
import com.sebaslogen.artai.domain.Navigator
import com.sebaslogen.artai.domain.components.DefaultRootComponent
import com.sebaslogen.artai.domain.components.RootComponent
import com.seiko.imageloader.ImageLoader
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@MainActivityScope
abstract class MainActivityDIComponent(
    @Component val parent: AndroidApplicationDIComponent,
    @get:Provides protected val componentContext: ComponentContext
) {
    abstract val dynamicUIRepositoryCreator: () -> DynamicUIRepository
    abstract val imageLoaderCreator: () -> ImageLoader
    abstract val defaultRootComponent: DefaultRootComponent

    @Provides
    fun rootComponent(): RootComponent = defaultRootComponent
}