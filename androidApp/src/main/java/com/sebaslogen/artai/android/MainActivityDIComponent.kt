package com.sebaslogen.artai.android

import com.sebaslogen.artai.android.di.components.AndroidApplicationDIComponent
import com.sebaslogen.artai.android.ui.components.FavoriteContainer
import com.sebaslogen.artai.data.remote.repositories.DynamicUIRepository
import com.sebaslogen.artai.di.components.AppComponentsDIComponent
import com.sebaslogen.artai.di.scopes.RootScreenScope
import com.seiko.imageloader.ImageLoader
import me.tatarka.inject.annotations.Component
import kotlin.coroutines.CoroutineContext

@Component
@RootScreenScope
abstract class MainActivityDIComponent(
    @Component val parent: AndroidApplicationDIComponent // Include all dependencies from the parent in this component
) {
    abstract val dynamicUIRepositoryCreator: () -> DynamicUIRepository
    abstract val imageLoaderCreator: () -> ImageLoader
    abstract val mainCoroutineContext: CoroutineContext
    abstract val appComponentsDIComponent: AppComponentsDIComponent
    abstract val favoriteContainer: FavoriteContainer
}