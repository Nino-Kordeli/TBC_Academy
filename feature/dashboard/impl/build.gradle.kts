plugins {
    alias(libs.plugins.tbcacademy.android.feature.impl)
    alias(libs.plugins.tbcacademy.android.library.compose)
}

android {
    namespace = "com.example.core.dashboard.impl"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.feature.dashboard.api)
    implementation(libs.androidx.activity.compose)
    implementation(projects.feature.workout.api)
    implementation(projects.feature.recipes.api)
}