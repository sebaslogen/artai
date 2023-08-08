package com.sebaslogen.artai.data.mappers

import com.sebaslogen.artai.data.remote.models.ApiAction
import com.sebaslogen.artai.data.remote.models.ApiCarouselItem
import com.sebaslogen.artai.data.remote.models.ApiFavorite
import com.sebaslogen.artai.data.remote.models.ApiFavoriteAction
import com.sebaslogen.artai.data.remote.models.ApiListItem
import com.sebaslogen.artai.data.remote.models.ApiScreenResponse
import com.sebaslogen.artai.data.remote.models.ApiSection
import com.sebaslogen.artai.data.remote.models.ApiSectionHeader
import com.sebaslogen.artai.domain.models.Action
import com.sebaslogen.artai.domain.models.CacheData
import com.sebaslogen.artai.domain.models.CarouselItem
import com.sebaslogen.artai.domain.models.DynamicUIDomainModel
import com.sebaslogen.artai.domain.models.Favorite
import com.sebaslogen.artai.domain.models.ListItem
import com.sebaslogen.artai.domain.models.Screen
import com.sebaslogen.artai.domain.models.Section
import com.sebaslogen.artai.domain.models.SectionHeader


fun ApiScreenResponse.mapToCacheData() = cache?.let {
    CacheData(
        favorites = it.favorites
    )
}

fun ApiScreenResponse.mapToSuccess(favorites: List<String>) = DynamicUIDomainModel.Success(this.mapToScreen(favorites))

private fun ApiScreenResponse.mapToScreen(favorites: List<String>): Screen = Screen(
    id = screen.id,
    sections = screen.sections.mapToSections(favorites)
)

private fun List<ApiSection>.mapToSections(favorites: List<String>): List<Section> = this.map { section ->
    when (section) {
        is ApiSection.ApiCarousel -> Section.Carousel(
            id = section.id,
            header = section.header.mapToSectionHeader(),
            style = section.style.mapToCarouselStyle(),
            items = section.items.mapToCarouselItems(favorites)
        )

        is ApiSection.ApiFooter -> Section.Footer(id = section.id, text = section.text)
        is ApiSection.ApiList -> Section.ListSection(id = section.id, header = section.header.mapToSectionHeader(), items = section.items.mapToListItems())
        is ApiSection.ApiUnknown -> Section.Unknown(type = section.type, id = section.id)
    }
}

private fun ApiSectionHeader.mapToSectionHeader(): SectionHeader = when (this) {
    is ApiSectionHeader.ApiLarge -> SectionHeader.Large(id = id, title = title)
    is ApiSectionHeader.ApiNormal -> SectionHeader.Normal(id = id, title = title)
    is ApiSectionHeader.ApiSmallArt -> SectionHeader.SmallArt(id = id, image = image, title = title, subtitle = subtitle)
    is ApiSectionHeader.ApiUnknown -> SectionHeader.Unknown(type = type, id = id)
}

private fun ApiSection.ApiCarousel.ApiCarouselStyle.mapToCarouselStyle(): Section.Carousel.CarouselStyle =
    when (this) {
        ApiSection.ApiCarousel.ApiCarouselStyle.Squared -> Section.Carousel.CarouselStyle.Squared
        ApiSection.ApiCarousel.ApiCarouselStyle.Circle -> Section.Carousel.CarouselStyle.Circle
        ApiSection.ApiCarousel.ApiCarouselStyle.RoundedSquares -> Section.Carousel.CarouselStyle.RoundedSquares
    }

private fun List<ApiCarouselItem>.mapToCarouselItems(favorites: List<String>): List<CarouselItem> = this.map { item ->
    when (item) {
        is ApiCarouselItem.ApiSmallArt -> CarouselItem.SmallArt(
            id = item.id,
            image = item.image,
            favorite = item.toFavorite(favorites),
            action = item.action.mapToAction()
        )

        is ApiCarouselItem.ApiUnknown -> CarouselItem.Unknown(type = item.type, id = item.id)
    }
}

private fun ApiFavorite.toFavorite(favorites: List<String>) = Favorite(
    id = this.id,
    favorited = favorites.contains(this.id),
    toggleFavoriteState = this.toToggleFavoriteState(favorites)
)

private fun ApiFavorite.toToggleFavoriteState(favorites: List<String>): Action.ToggleFavoriteState {
    val favorited = favorites.contains(id)
    return Action.ToggleFavoriteState(id = id, url = if (favorited) unFavoriteAction.url else favoriteAction.url)
}

private fun List<ApiListItem>.mapToListItems(): List<ListItem> = this.map { item ->
    when (item) {
        is ApiListItem.ApiBigArt -> ListItem.BigArt(id = item.id, title = item.title, image = item.image)
        is ApiListItem.ApiUnknown -> ListItem.Unknown(type = item.type, id = item.id)
    }
}

private fun ApiAction.mapToAction(): Action = when (this) {
    is ApiAction.ApiOpenScreen -> Action.OpenScreen(id = id, url = url)
    is ApiAction.ApiUnknown -> Action.Unknown(type = type, id = id)
    is ApiAction.ApiCommandAddToFavorites -> Action.ToggleFavoriteState(id = id, url = url)
    is ApiAction.ApiCommandRemoveFromFavorites -> Action.ToggleFavoriteState(id = id, url = url)
}