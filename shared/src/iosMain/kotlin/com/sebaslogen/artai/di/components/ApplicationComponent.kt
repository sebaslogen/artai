package com.sebaslogen.artai.di.components

import com.sebaslogen.artai.di.scopes.Singleton
import com.sebaslogen.artai.presentation.DynamicUIViewModel
import me.tatarka.inject.annotations.Component

@Component
@Singleton
abstract class ApplicationComponent : NetworkComponent, ApiServicesComponent {
    abstract val dynamicUIViewModel: DynamicUIViewModel
}