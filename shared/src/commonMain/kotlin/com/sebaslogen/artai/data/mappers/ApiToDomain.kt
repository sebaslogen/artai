package com.sebaslogen.artai.data.mappers

import com.sebaslogen.artai.data.remote.models.ApiAction
import com.sebaslogen.artai.data.remote.models.ApiCarouselItem
import com.sebaslogen.artai.data.remote.models.ApiListItem
import com.sebaslogen.artai.data.remote.models.ApiScreenResponse
import com.sebaslogen.artai.data.remote.models.ApiSection
import com.sebaslogen.artai.data.remote.models.ApiSectionHeader
import com.sebaslogen.artai.domain.models.Action
import com.sebaslogen.artai.domain.models.CacheData
import com.sebaslogen.artai.domain.models.CarouselItem
import com.sebaslogen.artai.domain.models.DynamicUIDomainModel
import com.sebaslogen.artai.domain.models.ListItem
import com.sebaslogen.artai.domain.models.Screen
import com.sebaslogen.artai.domain.models.Section
import com.sebaslogen.artai.domain.models.SectionHeader


fun ApiScreenResponse.mapToCacheData() = cache?.let {
    CacheData(
        favorites = it.favorites
    )
}

fun ApiScreenResponse.mapToSuccess() = DynamicUIDomainModel.Success(this.mapToScreen())

private fun ApiScreenResponse.mapToScreen(): Screen = Screen(
    id = screen.id,
    sections = screen.sections.mapToSections()
)

private fun List<ApiSection>.mapToSections(): List<Section> = this.map { section ->
    when (section) {
        is ApiSection.ApiCarousel -> Section.Carousel(
            id = section.id,
            header = section.header.mapToSectionHeader(),
            style = section.style.mapToCarouselStyle(),
            items = section.items.mapToCarouselItems()
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

private fun List<ApiCarouselItem>.mapToCarouselItems(): List<CarouselItem> = this.map { item ->
    when (item) {
        is ApiCarouselItem.ApiSmallArt -> CarouselItem.SmallArt(id = item.id, image = item.image, action = item.action.mapToAction())
        is ApiCarouselItem.ApiUnknown -> CarouselItem.Unknown(type = item.type, id = item.id)
    }
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
}