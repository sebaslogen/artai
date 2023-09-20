package com.sebaslogen.artai.android.di.components

import android.content.Context
import com.sebaslogen.artai.android.di.scopes.AndroidSingleton
import com.sebaslogen.artai.di.components.ApplicationDIComponent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@AndroidSingleton
abstract class AndroidApplicationDIComponent(
    @Component val parent: ApplicationDIComponent,
    @get:Provides val context: Context,
) : ImageLoaderDIComponent

interface ApplicationDIComponentProvider {
    val dependencyInjectionComponent: AndroidApplicationDIComponent
}

val Context.applicationDIComponent get() = (applicationContext as ApplicationDIComponentProvider).dependencyInjectionComponent