@file:OptIn(ExperimentalFoundationApi::class)

package com.sebaslogen.artai.android.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.unit.dp
import com.sebaslogen.artai.android.ui.utils.ImageLoaderImage
import com.sebaslogen.artai.domain.ActionHandler
import com.sebaslogen.artai.domain.models.CarouselItem
import com.sebaslogen.artai.domain.models.ListItem
import com.sebaslogen.artai.domain.models.Screen
import com.sebaslogen.artai.domain.models.Section
import com.sebaslogen.artai.domain.models.Section.*
import com.sebaslogen.artai.domain.models.Section.Carousel.CarouselStyle.*
import com.sebaslogen.artai.domain.models.SectionHeader


@Composable
fun ScreenContent(screen: Screen, onAction: ActionHandler) {
    Text("Screen id is: ${screen.id}")
    LazyColumn {
        screen.sections.forEach { section: Section ->
            when (section) {
                is Carousel -> carousel(section, onAction)
                is Footer -> item(key = section.id) { Text("TODO(Footer)") }
                is ListSection -> listSection(section)
                is Unknown -> item(key = section.id) { Text("TODO(Unknown)") }
            }
        }
    }
}

fun LazyListScope.listSection(section: ListSection) {
    when (section.header) {
        is SectionHeader.Large -> sectionHeaderLarge(section.header as SectionHeader.Large)
        is SectionHeader.Normal -> sectionHeaderNormal(section.header as SectionHeader.Normal)
        is SectionHeader.SmallArt -> sectionHeaderSmallArt(section.header as SectionHeader.SmallArt)
        is SectionHeader.Unknown -> sectionHeaderUnknown(section.header as SectionHeader.Unknown)
    }
    section.items.forEach { item ->
        item(key = item.id) {
            when (item) {
                is ListItem.BigArt -> {
                    ImageLoaderImage(
                        data = item.image,
                        contentDescription = "Img ${item.image}",
                        filterQuality = FilterQuality.Medium,
                        modifier = Modifier
                            .size(150.dp)
                            .animateItemPlacement()
                            .padding(12.dp)
                    )
                }
                is ListItem.Unknown -> Text("TODO(Unknown)", modifier = Modifier.animateItemPlacement())
            }
        }
    }
}

fun LazyListScope.carousel(section: Carousel, onAction: ActionHandler) {
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
                    Squared -> RoundedCornerShape(0)
                    Circle -> CircleShape
                    RoundedSquares -> RoundedCornerShape(8.dp)
                }
                when (item) {
                    is CarouselItem.SmallArt ->
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

                    is CarouselItem.Unknown -> Text("TODO(Unknown)", modifier = Modifier.animateItemPlacement())
                }
            }
        }
    }
}

fun LazyListScope.sectionHeaderLarge(header: SectionHeader.Large) {
    item(key = header.id) { Text(header.title, modifier = Modifier.animateItemPlacement()) }
}

fun LazyListScope.sectionHeaderNormal(header: SectionHeader.Normal) {
    item(key = header.id) { Text(header.title, modifier = Modifier.animateItemPlacement()) }
}

fun LazyListScope.sectionHeaderSmallArt(header: SectionHeader.SmallArt) {
    item(key = header.id) {
        Column {

            Text(header.title, modifier = Modifier.animateItemPlacement())
            Text(header.subtitle, modifier = Modifier.animateItemPlacement())
            ImageLoaderImage(
                data = header.image,
                contentDescription = "Img ${header.image}",
                filterQuality = FilterQuality.Medium,
                modifier = Modifier
                    .size(400.dp)
                    .animateItemPlacement()
                    .padding(12.dp)
            )
        }
    }
}

fun LazyListScope.sectionHeaderUnknown(header: SectionHeader.Unknown) {
    item(key = header.id) { Text("Unknown header", modifier = Modifier.animateItemPlacement()) }
}