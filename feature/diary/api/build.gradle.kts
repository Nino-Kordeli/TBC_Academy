plugins {
    alias(libs.plugins.tbcacademy.android.feature.api)
}

android {
    namespace = "com.example.core.diary.api"
}

dependencies {
    api(projects.core.navigation)
}