plugins {
    alias(libs.plugins.tbcacademy.android.feature.api)
}

android {
    namespace = "com.example.api"
}

dependencies {
    api(projects.core.navigation)
}