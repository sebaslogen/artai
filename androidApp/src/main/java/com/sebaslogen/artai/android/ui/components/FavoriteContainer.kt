package com.sebaslogen.artai.android.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sebaslogen.artai.domain.ActionHandler

@Composable
fun FavoriteContainer(
    onAction: ActionHandler,
    content: @Composable () -> Unit
) {
    Box {
        content()
        val imageVector = Icons.Filled.Favorite
        val contentDescription = "Favorited"
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = Color.Red,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
//                .clickable { onAction.onAction(item.action) } // TODO
        )
    }
}