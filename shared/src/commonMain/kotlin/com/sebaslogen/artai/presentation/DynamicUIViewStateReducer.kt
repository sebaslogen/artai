package com.sebaslogen.artai.presentation

import com.sebaslogen.artai.domain.models.CarouselItem
import com.sebaslogen.artai.domain.models.Favorite
import com.sebaslogen.artai.domain.models.ListItem
import com.sebaslogen.artai.domain.models.Section

object DynamicUIViewStateReducer {
    // TODO: We miss a favorites reducer here when the favorites are updated on the local cache before network response
    fun reduce(state: DynamicUIViewState, favorites: List<String>): DynamicUIViewState = when (state) {
        is DynamicUIViewState.Error, is DynamicUIViewState.Loading -> state
        is DynamicUIViewState.Success -> state.reduce(favorites)
    }

}

private fun DynamicUIViewState.Success.reduce(favorites: List<String>): DynamicUIViewState {
    val newScreen = data.copy(sections = data.sections.reduceListSection(favorites))
    return if (newScreen == data) this else DynamicUIViewState.Success(newScreen)
}

private fun List<Section>.reduceListSection(favorites: List<String>): List<Section> =
    map { section ->
        section.reduce(favorites)
    }

private fun Section.reduce(favorites: List<String>): Section = when (this) {
    is Section.Carousel -> this.reduce(favorites)
    is Section.ListSection -> this.reduce(favorites)
    is Section.Footer, is Section.Unknown -> this
}

private fun Section.Carousel.reduce(favorites: List<String>): Section.Carousel {
    val newSectionItems = items.reduceListCarouselItem(favorites)
    return if (newSectionItems == items) this else this.copy(items = newSectionItems)
}

private fun List<CarouselItem>.reduceListCarouselItem(favorites: List<String>): List<CarouselItem> =
    map { carouselItem ->
        carouselItem.reduce(favorites)
    }

private fun CarouselItem.reduce(favorites: List<String>): CarouselItem = when (this) {
    is CarouselItem.SmallArt -> this.reduce(favorites)
    is CarouselItem.Unknown -> this
}

private fun CarouselItem.SmallArt.reduce(favorites: List<String>): CarouselItem.SmallArt {
    val newFavorite: Favorite = favorite.reduce(favorites)
    return if (newFavorite == favorite) this else this.copy(favorite = newFavorite)
}

private fun Section.ListSection.reduce(favorites: List<String>): Section.ListSection {
    val newSectionItems = items.reduceListItems(favorites)
    return if (newSectionItems == items) this else this.copy(items = newSectionItems)
}

private fun List<ListItem>.reduceListItems(favorites: List<String>): List<ListItem> =
    map { listItem ->
        listItem.reduce(favorites)
    }

private fun ListItem.reduce(favorites: List<String>): ListItem = when (this) {
    is ListItem.BigArt -> this.reduce(favorites)
    is ListItem.Unknown -> this
}

private fun ListItem.BigArt.reduce(favorites: List<String>): ListItem.BigArt {
//    val newFavorite: Favorite = favorite.reduce(favorites) // TODO: Add favorites to BigArt
//    return if (newFavorite == favorite) this else this.copy(favorite = newFavorite)
    return this
}

private fun Favorite.reduce(favorites: List<String>): Favorite {
    val newFavState = favorites.contains(id)
    return if (newFavState == favorited) this else this.copy(favorited = favorites.contains(id))
}

