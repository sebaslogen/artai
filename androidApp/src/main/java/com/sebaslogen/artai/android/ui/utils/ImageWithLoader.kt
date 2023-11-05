package com.sebaslogen.artai.android.ui.utils

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.model.ImageEvent
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.model.ImageResult
import com.seiko.imageloader.rememberImageAction
import com.seiko.imageloader.rememberImageActionPainter
import io.github.aakira.napier.Napier


private enum class ImageLoadingState {
    Loading,
    Success,
    Failure,
}

@Composable
fun ImageLoaderImage(
    data: Any,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    errorModifier: Modifier = modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
    onLoading: (@Composable BoxScope.(Float) -> Unit)? = {
        CircularProgressIndicator()
    },
    onFailure: (@Composable BoxScope.(Throwable) -> Unit)? = {
        LaunchedEffect(it) {
            Napier.w(it) { "Error loading image" }
        }
        Box(
            modifier = errorModifier then Modifier
                .fillMaxSize()
                .background(Color(0x1F888888)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Rounded.Warning,
                contentDescription = null,
                tint = Color(0x1F888888),
                modifier = Modifier.size(24.dp),
            )
        }
    },
    contentAlignment: Alignment = Alignment.Center,
    animationSpec: FiniteAnimationSpec<Float>? = tween(),
) {
    key(data) {
        val progress = remember { mutableStateOf(-1F) }
        val error = remember { mutableStateOf<Throwable?>(null) }

        val request = remember { ImageRequest(data) }
        val action by rememberImageAction(request)
        val painter = rememberImageActionPainter(action = action, filterQuality = filterQuality)

        val state by remember {
            derivedStateOf {
                when (val currentAction = action) {
                    is ImageEvent.Start, ImageEvent.StartWithFetch -> {
                        progress.value = 0.0F
                        ImageLoadingState.Loading
                    }

                    is ImageResult.OfError -> {
                        progress.value = 0.0F
                        error.value = currentAction.error
                        ImageLoadingState.Failure
                    }

                    is ImageResult -> {
                        progress.value = 1.0F
                        ImageLoadingState.Success
                    }

                    else -> Unit
                }
            }
        }
        if (animationSpec != null) {
            Crossfade(state, animationSpec = animationSpec, modifier = modifier, label = "") {
                Box(Modifier.fillMaxSize(), contentAlignment) {
                    when (it) {
                        ImageLoadingState.Loading -> if (onLoading != null) {
                            onLoading(progress.value)
                        }

                        ImageLoadingState.Success -> Image(
                            painter = painter,
                            contentDescription = contentDescription,
                            modifier = Modifier.fillMaxSize(),
                            alignment = alignment,
                            contentScale = contentScale,
                            alpha = alpha,
                            colorFilter = colorFilter,
                        )

                        ImageLoadingState.Failure -> {
                            if (onFailure != null) {
                                onFailure(error.value ?: return@Crossfade)
                            }
                        }
                    }
                }
            }
        } else {
            Box(modifier, contentAlignment) {
                Image(
                    painter = painter,
                    contentDescription = contentDescription,
                    modifier = Modifier.fillMaxSize(),
                    alignment = alignment,
                    contentScale = contentScale,
                    alpha = alpha,
                    colorFilter = colorFilter,
                )
            }
        }
    }
}