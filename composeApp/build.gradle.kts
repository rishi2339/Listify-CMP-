import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqlDelight)
}
val flavor: String by project
val suffix = when (flavor) {
    "development" -> ".dev"
    else -> ""
}

kotlin {
    jvmToolchain(11)
    androidTarget {
        //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    }

    jvm()

    js {
        browser()
        binaries.executable()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.material)
            implementation(compose.components.resources)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.voyager.koin)
            implementation(libs.ktor.serialization)
            implementation(libs.bundles.voyager)
            implementation(project(":config:$flavor"))
            implementation("com.russhwolf:multiplatform-settings:1.1.1")
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.1.1")
            // implementation("co.touchlab:stately-common:2.0.5")
            implementation("com.russhwolf:multiplatform-settings-coroutines:1.1.1")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(libs.kotlinx.coroutines.test)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activityCompose)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqlDelight.driver.android)
            implementation("io.insert-koin:koin-android:3.6.0-wasm-alpha2")
            implementation("androidx.preference:preference-ktx:1.2.1")
            implementation("androidx.security:security-crypto:1.1.0-alpha03")
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqlDelight.driver.sqlite)
        }

        jsMain.dependencies {
            implementation(compose.html.core)
            implementation(libs.ktor.client.js)
            implementation(libs.sqlDelight.driver.js)
            implementation(devNpm("copy-webpack-plugin", "9.1.0"))
            implementation(npm("@cashapp/sqldelight-sqljs-worker", "2.0.1"))
            implementation(npm("sql.js", "1.8.0"))
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqlDelight.driver.native)
        }

    }
}

android {
    namespace = "com.cmp.todo"
    compileSdk = 35

    defaultConfig {
        when (flavor) {
            "development" -> resValue("string", "app_name", "Listify Dev")
            else -> resValue("string", "app_name", "Listify Prod")
        }
        minSdk = 21
        targetSdk = 35

        applicationId = "com.cmp.todo.androidApp"
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

//https://developer.android.com/develop/ui/compose/testing#setup
dependencies {
    androidTestImplementation(libs.androidx.uitest.junit4)
    debugImplementation(libs.androidx.uitest.testManifest)
    //temporary fix: https://youtrack.jetbrains.com/issue/CMP-5864
    androidTestImplementation("androidx.test:monitor") {
        version { strictly("1.6.1") }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.cmp.todo.desktop"
            packageVersion = "1.0.0"

//            linux {
//                iconFile.set(project.file("desktopAppIcons/LinuxIcon.png"))
//            }
//            windows {
//                iconFile.set(project.file("desktopAppIcons/WindowsIcon.ico"))
//            }
//            macOS {
//                iconFile.set(project.file("desktopAppIcons/MacosIcon.icns"))
//                bundleID = "com.cmp.todo.desktopApp"
//            }
        }
    }
}

sqldelight {
    databases {
        create("ToDoDatabase") {
            // Database configuration here.
            // https://cashapp.github.io/sqldelight
            packageName.set("com.cmp.todo.database")
            srcDirs("src/commonMain/sqldelight")
            generateAsync.set(true)
        }
    }
}
