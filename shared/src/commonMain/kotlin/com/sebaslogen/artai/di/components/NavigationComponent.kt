package com.sebaslogen.artai.di.components

import com.sebaslogen.artai.di.scopes.Singleton
import com.sebaslogen.artai.domain.DynamicUINavigationState
import kotlinx.coroutines.flow.MutableStateFlow
import me.tatarka.inject.annotations.Provides

interface NavigationComponent {

    /**
     * Global shared screen state. It's wrong because it should be a stack but it works for 1 screen deep navigation.
     */
    @Singleton
    @Provides
    fun navigationState() = MutableStateFlow<DynamicUINavigationState>(DynamicUINavigationState.HomeScreen)
}