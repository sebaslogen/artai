package com.sebaslogen.artai.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sebaslogen.artai.android.ui.components.ScreenContent
import com.sebaslogen.artai.domain.components.SDUIScreenComponent
import com.sebaslogen.artai.presentation.DynamicUIViewState

val LocalSDUIScreenComponent = createSDUIScreenComponentProvidableCompositionLocal()

class SDUIScreenComponentProvidableCompositionLocal(
    val delegate: ProvidableCompositionLocal<SDUIScreenComponent?>,
) {
    inline val current: SDUIScreenComponent
        @Composable
        @ReadOnlyComposable
        get() = delegate.current ?: throw IllegalStateException("SDUIScreenComponent has not been provided in CompositionLocalProvider")

    infix fun provides(value: SDUIScreenComponent): ProvidedValue<*> {
        return delegate provides value
    }
}

fun createSDUIScreenComponentProvidableCompositionLocal() = SDUIScreenComponentProvidableCompositionLocal(delegate = staticCompositionLocalOf { null })

@Composable
fun SDUIScreenContent(
    component: SDUIScreenComponent
) {
    CompositionLocalProvider(
        LocalSDUIScreenComponent provides component
    ) {
        val viewState: DynamicUIViewState by component.viewState.collectAsStateWithLifecycle()

        Column {
            val url = component.url.value
            if (url.isBlank()) {
                Text("Home")
            } else {
                Text("Remote url is ...${url.takeLast(20)}")
            }
            Spacer(modifier = Modifier.height(20.dp))
            when (val state = viewState) {
                is DynamicUIViewState.Error -> Text("Error loading data :(")
                DynamicUIViewState.Loading -> Text("Loading...")
                is DynamicUIViewState.Success -> ScreenContent(state.data, component)
            }
        }
    }
}