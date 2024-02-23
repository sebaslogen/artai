package com.sebaslogen.artai.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.sebaslogen.artai.domain.DynamicUINavigationState
import com.sebaslogen.artai.domain.models.CarouselItem
import com.sebaslogen.artai.domain.models.Screen
import com.sebaslogen.artai.domain.models.Section
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

class ViewModelsProvider() {
    fun getFavoritesViewModel(): FavoritesViewModel = TODO()
}

val LocalViewModelsProvider = staticCompositionLocalOf { ViewModelsProvider() }

@MoleculeComposable
@OptIn(InternalComposeApi::class)
fun <T> MyCompositionLocalProvider(vararg values: ProvidedValue<*>, content: @Composable () -> T): T {
    currentComposer.startProviders(values)
    val c = content()
    currentComposer.endProviders()
    return c
}

@MoleculeComposable
fun reduceScreenState(it: DynamicUIViewState, viewModelsProvider: ViewModelsProvider): DynamicUIViewState {
    return MyCompositionLocalProvider(LocalViewModelsProvider provides viewModelsProvider) {
        when (it) {
            is DynamicUIViewState.Error -> DynamicUIViewState.Error(it.error)
            DynamicUIViewState.Loading -> DynamicUIViewState.Loading
            is DynamicUIViewState.Success -> DynamicUIViewState.Success(reduceScreen(it.data))
        }
    }

}

@MoleculeComposable
private fun reduceScreen(screen: Screen): Screen {
    return screen.copy(sections = screen.sections.map { section ->
        key(section.id) {
            when (section) {
                is Section.Carousel -> reduceCarousel(section)
                is Section.Footer -> section
                is Section.ListSection -> section
                is Section.Unknown -> section
            }
        }
    })
}

@MoleculeComposable
private fun reduceCarousel(it: Section.Carousel): Section.Carousel {
    return it.copy(items = it.items.map { carouselItem ->
        key(carouselItem.id) {
            when (carouselItem) {
                is CarouselItem.SmallArt -> reduceSmallArts(carouselItem)
                is CarouselItem.Unknown -> carouselItem
            }
        }
    })
}

@MoleculeComposable
private fun reduceSmallArts(smallArt: CarouselItem.SmallArt): CarouselItem.SmallArt {
//    val favoritesViewModel = LocalViewModelsProvider.current.getFavoritesViewModel()
//    val isFavorite by favoritesViewModel.favoriteState(smallArt.id).collectAsState(initial = false)

    // TODO: Remove this and enable lines above when VM dependency injection is solved
    var isFavorite by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(5000)
        isFavorite = !isFavorite
    }

    return smallArt.copy(
        favorite = smallArt.favorite.copy(favorited = isFavorite)
    )
}

typealias MoleculeComposable = Composable