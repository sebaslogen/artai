package com.sebaslogen.artai.android.di.components

import android.content.Context
import com.sebaslogen.artai.di.components.ApplicationDIComponent
import com.sebaslogen.artai.di.scopes.ApplicationSingleton
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@ApplicationSingleton
abstract class AndroidApplicationDIComponent(
    @get:Provides val context: Context,
) : ApplicationDIComponent, ImageLoaderDIComponent

interface AndroidApplicationDIComponentProvider {
    val dependencyInjectionComponent: AndroidApplicationDIComponent
}

val Context.androidApplicationDIComponent get() = (applicationContext as AndroidApplicationDIComponentProvider).dependencyInjectionComponent