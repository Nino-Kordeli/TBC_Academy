plugins {
    alias(libs.plugins.tbcacademy.android.feature.impl)
    alias(libs.plugins.tbcacademy.android.library.compose)
}

android {
    namespace = "com.example.core.profile.impl"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.feature.profile.api)
    implementation(projects.feature.authentication.api)
    implementation(libs.androidx.activity.compose)

}