package com.sebaslogen.artai.android

import GrpcTestRepository
import android.app.Application
import com.connectrpc.ProtocolClientConfig
import com.connectrpc.impl.ProtocolClient
import com.connectrpc.okhttp.ConnectOkHttpClient
import com.connectrpc.protocols.NetworkProtocol
import com.sebaslogen.artai.McDScreen
import com.sebaslogen.artai.android.connect.adapter.PbandkStrategy
import com.sebaslogen.artai.android.di.components.ApplicationComponent
import com.sebaslogen.artai.android.di.components.ApplicationComponentProvider
import com.sebaslogen.artai.android.di.components.create
import com.sebaslogen.artai.globalRepo
import com.sebaslogen.artai.networking.ConnectGRPCClient
import com.sebaslogen.artai.toScreen
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import okhttp3.OkHttpClient
import screen.v1.GetScreenRequest
import screen.v1.GetScreenResponse

class ArtaiApplication : Application(), ApplicationComponentProvider {
    override val component by lazy(LazyThreadSafetyMode.NONE) {
        ApplicationComponent::class.create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        setupAppLogger()

        globalRepo = object : GrpcTestRepository {
            val okHttpClient = OkHttpClient()
            val protocolClient = ProtocolClient(
                httpClient = ConnectOkHttpClient(okHttpClient),
                config = ProtocolClientConfig(
                    host = "https://connect-poc-server-qpkwvfricq-ez.a.run.app",
                    serializationStrategy = PbandkStrategy(),
                    networkProtocol = NetworkProtocol.CONNECT
                )
            )
            val client = ConnectGRPCClient(protocolClient)
            override suspend fun sduiRequest(screenId: String): McDScreen {
                val response: GetScreenResponse = client.sendRequest(GetScreenRequest(screenId))
                return response.toScreen()
            }
        }
    }

    private fun setupAppLogger() {
        if (BuildConfig.DEBUG) {
            // Debug build
// TODO: Add and enable Crashlytics
//            // disable firebase crashlytics
//            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
            // init napier
            Napier.base(DebugAntilog())
        } else {
            // Others(Release build)

// TODO: Add and enable Crashlytics
//            // enable firebase crashlytics
//            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
            // init napier
            Napier.base(CrashlyticsAntilog(this))
        }
    }
}