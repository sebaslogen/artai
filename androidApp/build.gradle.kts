plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.sebaslogen.artai.android"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        applicationId = "com.sebaslogen.artai.android"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += setOf(
                // Exclude AndroidX version files
                "META-INF/*.version",
                // Exclude consumer proguard files
                "META-INF/proguard/*",
                // Exclude the Firebase/Fabric/other random properties files
                "/*.properties",
                "fabric/*.properties",
                "META-INF/*.properties",
                "/META-INF/{AL2.0,LGPL2.1}",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":shared"))

    // Kotlin-Inject for DI
    implementation(libs.kotlinInject.runtime)
    ksp(libs.kotlinInject.compiler)

    implementation(libs.napier.logger)
    implementation(libs.coroutines.android)

    // Compose dependencies and integration libs
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.toolingPreview)
    debugRuntimeOnly(libs.compose.ui.tooling)
    implementation(libs.compose.image.loader)

    implementation(libs.decompose.extensions.compose)
}