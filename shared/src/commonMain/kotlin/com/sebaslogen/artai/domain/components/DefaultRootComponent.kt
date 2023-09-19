package com.sebaslogen.artai.domain.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.sebaslogen.artai.domain.DynamicUINavigationState
import com.sebaslogen.artai.domain.components.RootComponent.*
import me.tatarka.inject.annotations.Inject


/**
 * Root component of the application that initializes
 * lifecycles, state restoration, instance keepers, back handlers and business logic.
 */
@Inject
class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    private val stack: Value<ChildStack<Config, Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.HomeScreen,
        handleBackButton = true, // Automatically pop from the stack on back button presses
        childFactory = ::createChild
    )
    override val childrenStack: Value<ChildStack<*, Child>> = stack

    /**
     * Navigation configuration at app level.
     * Note: This is a parallel version of [DynamicUINavigationState]
     */
    @Parcelize
    private sealed interface Config : Parcelable {
        object HomeScreen : Config // TODO: Use data object if possible

        data class RemoteScreen(val url: String) : Config
    }

    private fun createChild(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.HomeScreen -> Child.HomeScreen(HomeScreenComponent(componentContext))
            is Config.RemoteScreen -> Child.RemoteScreen(RemoteScreenComponent(componentContext, url = config.url))
        }
//    init {
//        lifecycle... // Access the Lifecycle
//        stateKeeper... // Access the StateKeeper
//        instanceKeeper... // Access the InstanceKeeper
//        backHandler... // Access the BackHandler
//    }
}