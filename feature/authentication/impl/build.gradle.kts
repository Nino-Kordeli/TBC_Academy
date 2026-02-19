import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.tbcacademy.android.feature.impl)
    alias(libs.plugins.tbcacademy.android.library.compose)
}

android {
    namespace = "com.example.core.authentication.impl"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(projects.feature.authentication.api)
    implementation(projects.tbcacademy.feature.quiz.api)
    implementation(projects.tbcacademy.feature.dashboard.api)

    implementation(projects.core.domain)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.hilt.navigation.compose)
}