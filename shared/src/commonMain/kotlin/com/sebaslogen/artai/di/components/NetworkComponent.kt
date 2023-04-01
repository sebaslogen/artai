package com.sebaslogen.artai.di.components
//
//
//import android.app.Application
//import me.tatarka.inject.annotations.Provides
//import okhttp3.Cache
//import okhttp3.ConnectionPool
//import okhttp3.Dispatcher
//import okhttp3.Interceptor
//import okhttp3.OkHttpClient
//import java.io.File
//import java.util.concurrent.TimeUnit
//
//interface NetworkComponent {
//    @ApplicationScope
//    @Provides
//    fun provideOkHttpClient(
//        context: Application,
//        interceptors: Set<Interceptor>,
//    ): OkHttpClient = OkHttpClient.Builder()
//        .apply { interceptors.forEach(::addInterceptor) }
//        // Around 4¢ worth of storage in 2020
//        .cache(Cache(File(context.cacheDir, "api_cache"), 50L * 1024 * 1024))
//        // Adjust the Connection pool to account for historical use of 3 separate clients
//        // but reduce the keepAlive to 2 minutes to avoid keeping radio open.
//        .connectionPool(ConnectionPool(10, 2, TimeUnit.MINUTES))
//        .dispatcher(
//            Dispatcher().apply {
//                // Allow for increased number of concurrent image fetches on same host
//                maxRequestsPerHost = 10
//            },
//        )
//        // Increase timeouts
//        .connectTimeout(20, TimeUnit.SECONDS)
//        .readTimeout(20, TimeUnit.SECONDS)
//        .writeTimeout(20, TimeUnit.SECONDS)
//        .build()
//}