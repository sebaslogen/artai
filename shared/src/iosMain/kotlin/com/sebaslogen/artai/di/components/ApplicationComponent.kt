package com.sebaslogen.artai.di.components

import com.sebaslogen.artai.PlatformGreeter
import com.sebaslogen.artai.di.scopes.Singleton
import me.tatarka.inject.annotations.Component

@Component
@Singleton
abstract class ApplicationComponent : PlatformComponent {
    abstract val platformGreeterCreator: () -> PlatformGreeter
}