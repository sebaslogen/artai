package com.sebaslogen.artai.di.components

import com.sebaslogen.artai.di.scopes.ApplicationSingleton
import com.sebaslogen.artai.di.scopes.IOSAppSingleton
import me.tatarka.inject.annotations.Component

@Component
@IOSAppSingleton
abstract class IOSApplicationDIComponent(@Component val parent: ApplicationDIComponent) {
//    abstract val dynamicUIViewModel: DynamicUIViewModel // TODO: iOS
}