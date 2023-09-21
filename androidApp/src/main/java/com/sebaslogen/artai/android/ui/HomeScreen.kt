package com.sebaslogen.artai.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sebaslogen.artai.android.ui.components.ScreenContent
import com.sebaslogen.artai.domain.components.HomeScreenComponent
import com.sebaslogen.artai.domain.components.SDUIScreenComponent
import com.sebaslogen.artai.presentation.DynamicUIViewModel
import com.sebaslogen.artai.presentation.DynamicUIViewState
import com.sebaslogen.artai.presentation.SDUIScreenComponentViewModel


/**
 * Home screen with manual refresh button hardcoded on screen and SDUI content below
 */
@Composable
fun HomeScreenContent(
    component: HomeScreenComponent,
) {
    val viewModel: SDUIScreenComponentViewModel = component.viewModel
    val viewState: DynamicUIViewState by viewModel.viewState.collectAsStateWithLifecycle()

    Column {
        Text("Home Screen content")
        Button(onClick = viewModel::onRefreshClicked) {
            Text("Refresh")
        }
        Spacer(modifier = Modifier.height(20.dp))
        when (val state = viewState) {
            is DynamicUIViewState.Error -> Text("Error loading data :(")
            DynamicUIViewState.Loading -> Text("Loading...")
            is DynamicUIViewState.Success -> ScreenContent(state.data, viewModel)
        }
    }
}

@Composable
fun SDUIScreenContent(
    component: SDUIScreenComponent
) {
    val viewModel: SDUIScreenComponentViewModel = component.viewModel
    val viewState: DynamicUIViewState by viewModel.viewState.collectAsStateWithLifecycle()

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
            is DynamicUIViewState.Success -> ScreenContent(state.data, viewModel)
        }
    }
}