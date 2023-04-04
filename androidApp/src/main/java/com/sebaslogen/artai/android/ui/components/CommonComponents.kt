package com.sebaslogen.artai.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sebaslogen.artai.data.remote.models.ApiCarouselItem
import com.sebaslogen.artai.data.remote.models.ApiScreen
import com.sebaslogen.artai.data.remote.models.ApiSection
import com.sebaslogen.artai.data.remote.models.ApiSectionHeader


@Composable
fun ScreenContent(screen: ApiScreen) {
    Text("Screen id is: ${screen.id}")
    LazyColumn {
        screen.sections.forEach { section: ApiSection ->
            when (section) {
                is ApiSection.ApiCarousel -> carousel(section)
                is ApiSection.ApiFooter -> item(key = section.id) { Text("TODO(ApiFooter)") }
                is ApiSection.ApiList -> item(key = section.id) { Text("TODO(ApiList)") }
                is ApiSection.ApiUnknown -> item(key = section.id) { Text("TODO(ApiUnknown)") }
            }
        }
    }
}

fun LazyListScope.carousel(section: ApiSection.ApiCarousel) {
    when (section.header) {
        is ApiSectionHeader.ApiLarge -> sectionHeaderLarge(section.header as ApiSectionHeader.ApiLarge)
        is ApiSectionHeader.ApiNormal -> sectionHeaderNormal(section.header as ApiSectionHeader.ApiNormal)
        is ApiSectionHeader.ApiSmallArt -> sectionHeaderSmallArt(section.header as ApiSectionHeader.ApiSmallArt)
        is ApiSectionHeader.ApiUnknown -> sectionHeaderUnknown(section.header as ApiSectionHeader.ApiUnknown)
    }
    item(key = section.id) {
        LazyRow {
            items(items = section.items,
                key = { item -> item.id ?: item.hashCode() },
                contentType = { item -> item::class }
            ) { item: ApiCarouselItem ->
                // TODO: Apply style section.style
                when (item) {
                    is ApiCarouselItem.ApiSmallArt ->
                        Text(
                            text = "Img ${item.image}",
                            modifier = Modifier
                                .padding(8.dp)
                                .background(color = Color(4278255617 * 31 * item.hashCode()), shape = RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        )

                    is ApiCarouselItem.ApiUnknown -> Text("TODO(ApiUnknown)")
                }
            }
        }
    }
}

fun LazyListScope.sectionHeaderLarge(header: ApiSectionHeader.ApiLarge) {
    item(key = header.id) { Text(header.title) }
}

fun LazyListScope.sectionHeaderNormal(header: ApiSectionHeader.ApiNormal) {
    item(key = header.id) { Text(header.title) }
}

fun LazyListScope.sectionHeaderSmallArt(header: ApiSectionHeader.ApiSmallArt) {
    item(key = header.id) { Text(header.title) }
}

fun LazyListScope.sectionHeaderUnknown(header: ApiSectionHeader.ApiUnknown) {
    item(key = header.id) { Text("Unknown header") }
}