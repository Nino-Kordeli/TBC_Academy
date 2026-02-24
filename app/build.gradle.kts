plugins {
    alias(libs.plugins.tbcacademy.android.application)
    alias(libs.plugins.tbcacademy.android.application.compose)
    alias(libs.plugins.tbcacademy.android.application.firebase)
    alias(libs.plugins.tbcacademy.hilt)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.serialization)
}

android {
    defaultConfig {
        applicationId = "com.example.tbcacademy"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions.unitTests.isIncludeAndroidResources = true
    namespace = "com.example.tbcacademy"
}

dependencies {
    // Feature modules
    implementation(projects.tbcacademy.feature.authentication.api)
    implementation(projects.tbcacademy.feature.authentication.impl)
    implementation(projects.tbcacademy.feature.quiz.api)
    implementation(projects.tbcacademy.feature.quiz.impl)
    implementation(projects.tbcacademy.feature.dashboard.api)
    implementation(projects.tbcacademy.feature.dashboard.impl)
    implementation(projects.tbcacademy.feature.diary.api)
    implementation(projects.tbcacademy.feature.diary.impl)
    implementation(projects.tbcacademy.feature.addFood.api)
    implementation(projects.tbcacademy.feature.addFood.impl)
    implementation(projects.tbcacademy.feature.addFoodDetails.api)
    implementation(projects.tbcacademy.feature.addFoodDetails.impl)
    implementation(projects.tbcacademy.feature.splash.api)
    implementation(projects.tbcacademy.feature.splash.impl)
    implementation(projects.tbcacademy.feature.profile.api)
    implementation(projects.tbcacademy.feature.profile.impl)
    implementation(projects.tbcacademy.feature.workout.api)
    implementation(projects.tbcacademy.feature.workout.impl)
    implementation(projects.tbcacademy.feature.recipes.api)
    implementation(projects.tbcacademy.feature.recipes.impl)


    // Core modules
    implementation(projects.core.common)
    implementation(projects.core.ui)
    implementation(projects.core.designsystem)
    implementation(projects.core.data)
    implementation(projects.core.model)

    // AndroidX + Compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.material)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.hilt.work)
    implementation(libs.work.runtime.ktx)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Hilt
    ksp(libs.hilt.compiler)
    ksp(libs.androidx.hilt.compiler)
}