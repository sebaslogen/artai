pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        maven { setUrl("https://androidx.dev/storage/compose-compiler/repository/") } // Required for Dev versions of Compose compiler
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "ArtAI"
include(":androidApp")
include(":shared")