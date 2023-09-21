package com.sebaslogen.artai.android

import com.arkivanov.decompose.ComponentContext
import com.sebaslogen.artai.android.di.components.AndroidApplicationDIComponent
import com.sebaslogen.artai.data.remote.repositories.DynamicUIRepository
import com.sebaslogen.artai.di.components.AppComponentsDIComponent
import com.sebaslogen.artai.di.scopes.MainActivityScope
import com.sebaslogen.artai.domain.components.NavRootComponent
import com.sebaslogen.artai.domain.components.RootComponent
import com.seiko.imageloader.ImageLoader
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import kotlin.coroutines.CoroutineContext

@Component
@MainActivityScope
abstract class MainActivityDIComponent(
    @Component val parent: AndroidApplicationDIComponent // Include all dependencies from the parent in this component
) {
    abstract val dynamicUIRepositoryCreator: () -> DynamicUIRepository
    abstract val imageLoaderCreator: () -> ImageLoader
    abstract val mainCoroutineContext: CoroutineContext
    abstract val appComponentsDIComponent: AppComponentsDIComponent
}