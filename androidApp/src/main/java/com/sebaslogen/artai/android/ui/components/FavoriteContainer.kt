package com.sebaslogen.artai.android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sebaslogen.artai.domain.ActionHandler
import com.sebaslogen.artai.domain.models.Favorite

@Composable
fun FavoriteContainer(
    favorite: Favorite,
    onAction: ActionHandler,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box {
        content()
        val imageVector = if (favorite.favorited) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
        val contentDescription = "Favorited"
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = Color.Red,
            modifier = modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
                .clickable { onAction.onAction(favorite.toggleFavoriteState) }
        )
    }
}