plugins {
    alias(libs.plugins.tbcacademy.android.feature.impl)
    alias(libs.plugins.tbcacademy.android.library.compose)
}

android {
    namespace = "com.example.core.quiz.impl"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(projects.feature.quiz.api)
    implementation(projects.feature.dashboard.api)

    implementation(projects.core.domain)
    implementation(libs.androidx.activity.compose)
}