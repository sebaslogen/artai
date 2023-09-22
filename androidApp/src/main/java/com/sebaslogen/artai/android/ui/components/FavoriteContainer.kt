package com.sebaslogen.artai.android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arkivanov.decompose.ComponentContext
import com.sebaslogen.artai.android.ui.LocalSDUIScreenComponent
import com.sebaslogen.artai.android.ui.SDUIScreenContent
import com.sebaslogen.artai.domain.ActionHandler
import com.sebaslogen.artai.domain.ActionHandlerSync
import com.sebaslogen.artai.domain.components.FavoriteComponent
import com.sebaslogen.artai.domain.components.SDUIScreenComponent
import com.sebaslogen.artai.domain.models.Favorite
import com.sebaslogen.artai.domain.usecases.FavoritesUseCase
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Declare a CompositionLocal that will provide the injected [FavoriteContainer] Composable function.
 * This way we can access the function plus its dependencies anywhere in the Compose tree where the CompositionLocal has been set.
 */
val LocalFavoriteContainer = createFavoriteContainerProvidableCompositionLocal()

class FavoriteContainerProvidableCompositionLocal constructor(
    val delegate: ProvidableCompositionLocal<FavoriteContainer?>,
) {
    inline val current: FavoriteContainer
        @Composable
        @ReadOnlyComposable
        get() = delegate.current ?: throw IllegalStateException("Favorite Composable has not been provided in CompositionLocalProvider")

    infix fun provides(value: FavoriteContainer): ProvidedValue<*> {
        return delegate provides value
    }
}

fun createFavoriteContainerProvidableCompositionLocal() = FavoriteContainerProvidableCompositionLocal(delegate = staticCompositionLocalOf { null })

/**
 * Define the injection function provider plus the Composable function to be injected below
 */
typealias FavoriteContainer = @Composable (favorite: Favorite, modifier: Modifier, content: @Composable () -> Unit) -> Unit

/**
 * Inject the dependencies required to create a [FavoriteComponent] (aka ViewModel) via DI and
 * the last two dependencies ([ComponentContext] and [CoroutineContext]) required to create a [FavoriteComponent]
 * will be provided by the parent [SDUIScreenComponent].
 * The parent [SDUIScreenComponent] will be provided by a LocalComposition that is set when a [SDUIScreenComponent]
 * is used in a [SDUIScreenContent] Composable.
 */
@Inject
@Composable
fun FavoriteContainer(
    @Assisted favorite: Favorite,
    favoritesUseCase: FavoritesUseCase,
    actionHandler: ActionHandlerSync,
    @Assisted modifier: Modifier = Modifier,
    @Assisted content: @Composable () -> Unit
) {
    val sduiScreenComponent: SDUIScreenComponent = LocalSDUIScreenComponent.current
    val favoriteComponent: FavoriteComponent = sduiScreenComponent.attachSiblingComponent { componentContext, coroutineContext ->
        FavoriteComponent(
            componentContext = componentContext,
            coroutineContext = coroutineContext,
            favoritesUseCase = favoritesUseCase,
            actionHandler = actionHandler
        )
    }
    val updatedFavorite by favoriteComponent.favoriteState(favorite).collectAsStateWithLifecycle(initialValue = favorite)
    FavoriteContainer(favorite = updatedFavorite, onAction = favoriteComponent, modifier = modifier, content = content)
}

@Composable
fun FavoriteContainer(
    favorite: Favorite,
    onAction: ActionHandler,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box {
        content()
        val imageVector = if (favorite.favorited) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
        val contentDescription = "Favorited"
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = Color.Red,
            modifier = modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
                .clickable { onAction.onAction(favorite.toggleFavoriteState) }
        )
    }
}