@file:OptIn(ExperimentalFoundationApi::class)

package com.sebaslogen.artai.android.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
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
                is ListSection -> listSection(section, onAction)
                is Unknown -> item(key = section.id) { Text("TODO(Unknown)") }
            }
        }
    }
}

