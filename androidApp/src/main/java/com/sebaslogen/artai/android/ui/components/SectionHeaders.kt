@file:OptIn(ExperimentalFoundationApi::class)

package com.sebaslogen.artai.android.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.unit.dp
import com.sebaslogen.artai.android.ui.utils.ImageLoaderImage
import com.sebaslogen.artai.domain.models.SectionHeader

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