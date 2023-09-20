package com.sebaslogen.artai.di.components

import com.sebaslogen.artai.di.scopes.ApplicationSingleton
import me.tatarka.inject.annotations.Component

@Component
@ApplicationSingleton
abstract class ApplicationDIComponent : NetworkDIComponent, ApiServicesDIComponent {
//    abstract val dynamicUIViewModel: DynamicUIViewModel // TODO: iOS
}