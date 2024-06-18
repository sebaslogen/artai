package com.sebaslogen.artai.di.components

import com.sebaslogen.artai.di.scopes.ApplicationSingleton
import me.tatarka.inject.annotations.Component

@Component
@ApplicationSingleton
abstract class IOSApplicationDIComponent : ApplicationDIComponent {
//    abstract val dynamicUIViewModel: DynamicUIViewModel // TODO: iOS
}
