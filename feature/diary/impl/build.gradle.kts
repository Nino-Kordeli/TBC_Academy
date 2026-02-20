plugins {
    alias(libs.plugins.tbcacademy.android.feature.impl)
    alias(libs.plugins.tbcacademy.android.library.compose)
}

android {
    namespace = "com.example.core.diary.impl"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.feature.diary.api)
    implementation(projects.feature.addFood.api)
    implementation(libs.androidx.activity.compose)

}