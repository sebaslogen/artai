package com.sebaslogen.artai.android.di.components

import android.content.Context
import com.sebaslogen.artai.di.components.PlatformComponent
import com.sebaslogen.artai.di.scopes.Singleton
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@Singleton
abstract class ApplicationComponent(
        @get:Provides val context: Context,
) : PlatformComponent

interface ApplicationComponentProvider {
    val component: ApplicationComponent
}

val Context.applicationComponent get() = (applicationContext as ApplicationComponentProvider).component