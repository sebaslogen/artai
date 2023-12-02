import com.codingfeline.buildkonfig.compiler.FieldSpec.Type

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.native.cocoapods) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.buildkonfig) apply false
    alias(libs.plugins.ktorfit) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }
    // Generate BuildKonfig.kt with constants for each module
    plugins.withType<com.codingfeline.buildkonfig.gradle.BuildKonfigPlugin> {
        configure<com.codingfeline.buildkonfig.gradle.BuildKonfigExtension> {
            defaultConfigs {
                buildConfigField(Type.STRING, "NAME", rootProject.name, const = true)
                buildConfigField(Type.BOOLEAN, "DEBUG", project.hasProperty("debugApp").toString(), const = true)
                plugins.withType<com.android.build.gradle.BasePlugin> {
                    buildConfigField(Type.BOOLEAN, "DEBUG", gradle.startParameter.taskRequests.toString().contains("Debug").toString(), const = true)
                }
            }
        }
    }
}