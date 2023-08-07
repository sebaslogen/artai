@file:OptIn(ExperimentalFoundationApi::class)

package com.sebaslogen.artai.android.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.unit.dp
import com.sebaslogen.artai.android.ui.utils.ImageLoaderImage
import com.sebaslogen.artai.domain.ActionHandler
import com.sebaslogen.artai.domain.models.CarouselItem
import com.sebaslogen.artai.domain.models.Section
import com.sebaslogen.artai.domain.models.SectionHeader


fun LazyListScope.carousel(section: Section.Carousel, onAction: ActionHandler) {
    when (section.header) {
        is SectionHeader.Large -> sectionHeaderLarge(section.header as SectionHeader.Large)
        is SectionHeader.Normal -> sectionHeaderNormal(section.header as SectionHeader.Normal)
        is SectionHeader.SmallArt -> sectionHeaderSmallArt(section.header as SectionHeader.SmallArt)
        is SectionHeader.Unknown -> sectionHeaderUnknown(section.header as SectionHeader.Unknown)
    }
    item(key = section.id) {
        LazyRow(modifier = Modifier.animateItemPlacement()) {
            items(items = section.items,
                key = { item -> item.id ?: item.hashCode() },
                contentType = { item -> item::class }
            ) { item: CarouselItem ->
                val shape = when (section.style) {
                    Section.Carousel.CarouselStyle.Squared -> RoundedCornerShape(0)
                    Section.Carousel.CarouselStyle.Circle -> CircleShape
                    Section.Carousel.CarouselStyle.RoundedSquares -> RoundedCornerShape(8.dp)
                }
                when (item) {
                    is CarouselItem.SmallArt -> CarouselSmallArt(item, shape, onAction)
                    is CarouselItem.Unknown -> Text("TODO(CarouselItem.Unknown)", modifier = Modifier.animateItemPlacement())
                }
            }
        }
    }
}

@Composable
private fun LazyItemScope.CarouselSmallArt(
    item: CarouselItem.SmallArt,
    shape: RoundedCornerShape,
    onAction: ActionHandler
) {
    Box {
        ImageLoaderImage(
            data = item.image,
            contentDescription = "Img ${item.image}",
            filterQuality = FilterQuality.Medium,
            modifier = Modifier
                .size(150.dp)
                .animateItemPlacement()
                .padding(12.dp)
                .clip(shape = shape)
                .clickable { onAction.onAction(item.action) }
        )
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Favorited",
            modifier = Modifier.align(Alignment.TopEnd).padding(12.dp),
            tint = Color.Red
        )
    }
}