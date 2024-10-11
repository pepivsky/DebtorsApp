plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.googleGmsGoogleServices)
    alias(libs.plugins.googleFirebaseCrashlytics)
    alias(libs.plugins.compose.compiler)
    id("com.google.devtools.ksp")
    alias(libs.plugins.kotlinxSerialization)

}

android {
    namespace = "com.pepivsky.debtorsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pepivsky.debtorsapp"
        minSdk = 27
        targetSdk = 34
        versionCode = 33
        versionName = "1.3.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // export schema, necesario para las migraciones automaticas
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // enable in release builds
            extra["enableCrashlytics"] = true
            extra["alwaysUpdateBuildId"] = true
            // enable crashlytics in release
            manifestPlaceholders["crashlyticsCollectionEnabled"] = true
        }

        debug {
            // optimize crashlytics usage in debug builds
            extra["enableCrashlytics"] = false
            extra["alwaysUpdateBuildId"] = false
            // disable crashlytics in debug
            manifestPlaceholders["crashlyticsCollectionEnabled"] = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    /*composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }*/
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout.compose)
    implementation (libs.androidx.material.icons.extended)

    // dagger hilt
    /*implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)*/
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Room
    implementation (libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)

    // navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)


    // ads
    implementation (libs.play.services.ads)

// splashScreen
    implementation (libs.androidx.core.splashscreen)

    // crashlitycs
    implementation(libs.firebase.crashlytics)

    // in app reviews
    implementation (libs.review.ktx)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}