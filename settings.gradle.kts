pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        maven { setUrl("https://androidx.dev/storage/compose-compiler/repository/") } // Required for Dev versions of Compose compiler
        mavenCentral()
    }
}

rootProject.name = "ArtAI"
include(":androidApp")
include(":shared")