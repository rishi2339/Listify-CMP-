plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "${JavaVersion.VERSION_17}"
            }
        }
    }
    jvm()
    js {
        browser {
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "composeApp"
            isStatic = true
        }
    }
    task("testClasses")
    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            //implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.cmp.todo"
    compileSdk = 35
    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

