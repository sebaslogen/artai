package com.sebaslogen.artai.di.components

import com.sebaslogen.artai.di.scopes.ApplicationSingleton
import kotlinx.coroutines.Dispatchers
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import kotlin.coroutines.CoroutineContext

@Component
@ApplicationSingleton
abstract class ApplicationDIComponent : NetworkDIComponent, ApiServicesDIComponent, ViewModelsDIComponent {

    @Provides
    fun mainCoroutineContext(): CoroutineContext = Dispatchers.Main

    @Provides
    fun viewModelsDIComponent(): ViewModelsDIComponent = this

}