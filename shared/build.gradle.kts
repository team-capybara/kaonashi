plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.mokoResources)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
            export(libs.kmpnotifier)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines.core)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.decompose)
            implementation(libs.decompose.compose)
            implementation(libs.decompose.router)
            implementation(libs.essenty.parcelable)

            implementation(libs.ktor.client)
            implementation(libs.ktor.logging)
            implementation(libs.ktor.content.negotiation)
            implementation(libs.ktor.serialization.json)
            implementation(libs.logback.classic)

            implementation(libs.koin.core)

            implementation(libs.moko.mvvm.core)
            implementation(libs.moko.mvvm.compose)
            implementation(libs.moko.mvvm.flow)

            implementation(libs.moko.permissions.core)
            implementation(libs.moko.permissions.compose)

            api(libs.moko.resources)
            api(libs.moko.resources.compose)

            implementation(libs.webview.multiplatform)

            implementation(libs.settings)
            implementation(libs.settings.noarg)

            api(libs.kmpnotifier)
            implementation(libs.stately.common)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.koin.androidx.compose)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "team.capybara.moime"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

multiplatformResources {
    resourcesPackage.set("team.capybara.moime")
    resourcesClassName.set("SharedRes")
}
