package com.sebaslogen.artai.di.components

import com.sebaslogen.artai.PlatformGreeter
import com.sebaslogen.artai.di.scopes.Singleton
import com.sebaslogen.artai.domain.DynamicUIViewModel
import me.tatarka.inject.annotations.Component

@Component
@Singleton
abstract class ApplicationComponent : PlatformComponent, NetworkComponent, ApiServicesComponent {
    abstract val platformGreeterCreator: () -> PlatformGreeter
    abstract val dynamicUIViewModel: DynamicUIViewModel
}