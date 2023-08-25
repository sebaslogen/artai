@file:OptIn(ExperimentalFoundationApi::class)

package com.sebaslogen.artai.android.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.unit.dp
import com.sebaslogen.artai.android.ui.utils.ImageLoaderImage
import com.sebaslogen.artai.domain.ActionHandler
import com.sebaslogen.artai.domain.models.ListItem
import com.sebaslogen.artai.domain.models.Section
import com.sebaslogen.artai.domain.models.SectionHeader


fun LazyListScope.listSection(section: Section.ListSection, onAction: ActionHandler) {
    when (section.header) {
        is SectionHeader.Large -> sectionHeaderLarge(section.header as SectionHeader.Large)
        is SectionHeader.Normal -> sectionHeaderNormal(section.header as SectionHeader.Normal)
        is SectionHeader.SmallArt -> sectionHeaderSmallArt(section.header as SectionHeader.SmallArt, onAction)
        is SectionHeader.Unknown -> sectionHeaderUnknown(section.header as SectionHeader.Unknown)
    }
    section.items.forEach { item ->
        item(key = item.id) {
            when (item) {
                is ListItem.BigArt -> {
                    ListItemBigArt(item)
                }

                is ListItem.Unknown -> Text("TODO(ListItem.Unknown)", modifier = Modifier.animateItemPlacement())
            }
        }
    }
}

@Composable
private fun LazyItemScope.ListItemBigArt(item: ListItem.BigArt) {
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