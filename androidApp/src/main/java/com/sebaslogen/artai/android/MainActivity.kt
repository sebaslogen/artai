package com.sebaslogen.artai.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sebaslogen.artai.Greeting
import com.sebaslogen.artai.PlatformGreeter
import com.sebaslogen.artai.android.di.components.ApplicationComponent
import com.sebaslogen.artai.android.di.components.applicationComponent
import com.sebaslogen.artai.data.remote.repositories.DynamicUIRepository
import com.sebaslogen.artai.domain.DynamicUIViewModel
import com.sebaslogen.artai.networking.Http
import io.github.aakira.napier.Napier
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Inject

typealias Greeters = @Composable (PlatformGreeter) -> Unit

@Inject
@Composable
fun Greeters(dynamicUiViewModel: () -> DynamicUIViewModel, platformGreeter: PlatformGreeter) {
    val viewModel = viewModel { dynamicUiViewModel() }
    LaunchedEffect(Unit) {
        val home = viewModel.getHome()
        val screen = home.screen
        Napier.d { "ViewModel Response was: $screen with id: ${screen.id}" }
    }
    val platformGreet = remember { Greeting().greet() }
    GreetingView(platformGreet)
    val injectedGreet = remember { platformGreeter.greet() }
    GreetingView(injectedGreet)
}

@Component
abstract class MainActivityComponent(@Component val parent: ApplicationComponent) {
    abstract val platformGreeterCreator: () -> PlatformGreeter
    abstract val dynamicUIRepositoryCreator: () -> DynamicUIRepository
    abstract val dynamicUIViewModel: DynamicUIViewModel
    abstract val greeters: Greeters
}

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Napier.d { "Starting MainActivity. Debug build: ${BuildConfig.DEBUG}" }
        val mainActivityComponent = MainActivityComponent::class.create(applicationComponent)
        httpCall(mainActivityComponent)

        val platformGreeter: PlatformGreeter = mainActivityComponent.platformGreeterCreator()
        val greeters = mainActivityComponent.greeters
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        greeters(platformGreeter)
                    }
                }
            }
        }
    }

    private fun httpCall(mainActivityComponent: MainActivityComponent) {
        // Vanilla Ktor
        lifecycle.coroutineScope.launch {
            val http: Http = mainActivityComponent.parent.httpFactory()
            val response = http.get("https://raw.githubusercontent.com/sebaslogen/artai/main/fake-backend/home.json")
            val bodyAsText = response.bodyAsText()
            Napier.d { bodyAsText }
            val screen = mainActivityComponent.dynamicUIRepositoryCreator().home().screen
            Napier.d { "Response was: $screen with id: ${screen.id}" }
        }

    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}