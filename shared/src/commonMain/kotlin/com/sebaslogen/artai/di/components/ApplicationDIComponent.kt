package com.sebaslogen.artai.di.components

import com.sebaslogen.artai.di.scopes.ApplicationSingleton
import kotlinx.coroutines.Dispatchers
import me.tatarka.inject.annotations.Provides
import kotlin.coroutines.CoroutineContext

interface ApplicationDIComponent : NetworkDIComponent, ApiServicesDIComponent, AppComponentsDIComponent {

    @ApplicationSingleton
    @Provides
    fun mainCoroutineContext(): CoroutineContext = Dispatchers.Main

    @ApplicationSingleton
    @Provides
    fun appComponentsDIComponent(): AppComponentsDIComponent = this

}