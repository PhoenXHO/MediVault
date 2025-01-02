plugins {
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")

    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.ensas.medivault"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ensas.medivault"
        minSdk = 24
        //noinspection EditedTargetSdkVersion
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders["STARTUP_TRACE_ENABLED"] = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // App dependencies
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.coil.compose) // Coil for loading images
    implementation(libs.coil.network.okhttp) // Coil's OkHttp extension for network loading
    implementation(libs.androidx.navigation.compose) // Navigation for Jetpack Compose
    implementation(libs.hilt.android) // Hilt for dependency injection

    // Room dependencies
    implementation(libs.androidx.room.ktx)
    implementation(libs.googleid)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.storage)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.androidx.room.compiler)

    // Hilt dependencies
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler.v2511)

    // Firebase dependencies
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.core)
    implementation(libs.play.services.auth)

    // Credential Manager dependencies
    implementation(libs.androidx.credentials)

    // DataStore dependencies
    implementation(libs.androidx.datastore.preferences)


    // Compose dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

apply(plugin = "com.google.gms.google-services")