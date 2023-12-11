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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packagingOptions {
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
    packaging { // Require to compile google protobuf lib in pbandk & connect-kotlin-google-java-ext (error duplicated any.proto)
        resources.pickFirsts.add("google/protobuf/*.proto")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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
    implementation(libs.androidx.navigation.compose)
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

    implementation(libs.kmm.viewmodel)

    // TODO: Move all of these into a library built on the backend
    // Required to build the parsing of the protobuf response in PbandkStrategy
    implementation(libs.connect.kotlin.okhttp)
    // Java specific dependencies for protocol buffers
    implementation(libs.connect.kotlin.google.java.ext)
    implementation(libs.pbandk.runtime)
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.10")
}