plugins {
    alias(libs.plugins.tbcacademy.android.feature.impl)
    alias(libs.plugins.tbcacademy.android.library.compose)
}

android {
    namespace = "com.example.core.splash.impl"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.feature.splash.api)
    implementation(libs.androidx.activity.compose)

    implementation(projects.tbcacademy.feature.authentication.api)
    implementation(projects.tbcacademy.feature.dashboard.api)
}