@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.raju.movie.app"
    compileSdk = libs.versions.android.sdk.compile.get().toInt()

    defaultConfig {
        applicationId = "com.raju.movie.app"
        minSdk = libs.versions.android.sdk.min.get().toInt()
        targetSdk = libs.versions.android.sdk.target.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(projects.design)

    implementation(libs.core)
    implementation(libs.lifecycle.runtime)

    // compose
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)

    // Choose one of the following:
    implementation(libs.bundles.compose)
    implementation(libs.compose.activity)
    implementation(libs.compose.viewmodel)
    implementation(libs.compose.livedata)
    implementation(libs.compose.navigation)

    implementation(libs.compose.coil)
    implementation(libs.compose.lottie)

    // dagger hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.compose.hilt.navigation)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.integration)
    androidTestImplementation(libs.espresso)

    // UI Tests
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.compose.junit)
    debugImplementation(libs.compose.test.manifest)
}