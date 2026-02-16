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
    implementation(projects.tbcacademy.feature.authentication.api)
    implementation(projects.tbcacademy.feature.authentication.impl)

    implementation(projects.tbcacademy.feature.quiz.api)
    implementation(projects.tbcacademy.feature.quiz.impl)

    implementation(projects.tbcacademy.feature.dashboard.api)
    implementation(projects.tbcacademy.feature.dashboard.impl)

    implementation(projects.core.common)
    implementation(projects.core.ui)
    implementation(projects.core.designsystem)
    implementation(projects.core.data)

    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
    implementation(libs.androidx.compose.material3.adaptive.navigation3)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewModel.navigation3)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.tracing.ktx)
    implementation(libs.androidx.window.core)
    implementation(libs.coil)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.material)
    ksp(libs.hilt.compiler)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)
}