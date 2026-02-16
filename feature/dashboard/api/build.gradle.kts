plugins {
    alias(libs.plugins.tbcacademy.android.feature.api)
}

android {
    namespace = "com.example.core.dashboard.api"
}

dependencies {
    api(projects.core.navigation)
}