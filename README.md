[![Android Build CI](https://github.com/sebaslogen/artai/actions/workflows/android.yml/badge.svg)](https://github.com/sebaslogen/artai/actions/workflows/android.yml)
[![iOS Build CI](https://github.com/sebaslogen/artai/actions/workflows/ios-build.yml/badge.svg)](https://github.com/sebaslogen/artai/actions/workflows/ios-build.yml)
[![GitHub license](https://img.shields.io/github/license/sebaslogen/resaca)](https://github.com/sebaslogen/resaca/blob/main/LICENSE)

# ArtAI

Art gallery experimentation app for Server Driven UI in Kotlin Mobile Multiplatform

## Libraries integrated

- Kotlin Mobile Multiplatform
- SwiftUI for iOS UI
- Jetpack Compose for Android UI
- Material design 3 for Android's design
- Kotlin-Inject for multiplatform dependency injection
- Kotlin coroutines
- Napier as multiplatform logger
- Cocoapods for package management in iOS
- Ktor
- BuildKonfig
- Kotlin serialization
- Ktorfit
- KMM-ViewModel
- KMP-NativeCoroutines
- SKIE: Wrap KMP Objective-C in a nice Swift API

## Before running!

- Install and check your system with (KDoctor)[https://github.com/Kotlin/kdoctor]
- Install JDK 11 on your machine
- add `local.properties` file to the project root and set a path to Android SDK there
- run `./gradlew podInstall` in the project root

### Android

To run the application on android device/emulator:

- open project in Android Studio and run imported android run configuration

To build the application bundle:

- run `./gradlew :artai:assembleDebug`
- find `.apk` file in `androidApp/build/outputs/apk/debug/androidApp-debug.apk`

### iOS

To run the application on iPhone device/simulator:

- Open `iosApp/iosApp.xcworkspace` in Xcode and run standard configuration
- Or use (Kotlin Multiplatform Mobile plugin)[https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile] for Android Studio
