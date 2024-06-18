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
import com.sebaslogen.artai.domain.ActionHandler
import com.sebaslogen.artai.domain.models.Favorite
import com.sebaslogen.artai.presentation.FavoritesViewModel
import com.sebaslogen.resaca.viewModelScoped
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

val LocalFavoriteContainer = createFavoriteContainerProvidableCompositionLocal()

class FavoriteContainerProvidableCompositionLocal constructor(
    val delegate: ProvidableCompositionLocal<FavoriteContainer?>,
) {
    inline val current: FavoriteContainer
        @Composable
        @ReadOnlyComposable
        get() = delegate.current ?: throw IllegalStateException("Favorite Composable has not been injected")

    infix fun provides(value: FavoriteContainer): ProvidedValue<*> {
        return delegate provides value
    }
}

fun createFavoriteContainerProvidableCompositionLocal() = FavoriteContainerProvidableCompositionLocal(delegate = staticCompositionLocalOf { null })

typealias FavoriteContainer = @Composable (favorite: Favorite, modifier: Modifier, content: @Composable () -> Unit) -> Unit

@Inject
@Composable
fun FavoriteContainer(
    @Assisted favorite: Favorite,
    favoritesViewModelProvider: () -> FavoritesViewModel,
    @Assisted modifier: Modifier = Modifier,
    @Assisted content: @Composable () -> Unit
) {
    val favoritesViewModel = viewModelScoped { favoritesViewModelProvider() }
    val updatedFavorite by favoritesViewModel.favoriteState(favorite).collectAsStateWithLifecycle(initialValue = favorite)
    FavoriteContainer(favorite = updatedFavorite, onAction = favoritesViewModel, modifier = modifier, content = content)
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