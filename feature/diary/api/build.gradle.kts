plugins {
    alias(libs.plugins.tbcacademy.android.feature.api)
}

android {
    namespace = "com.example.feature.diary.api"
}

dependencies {
    api(projects.core.navigation)
}