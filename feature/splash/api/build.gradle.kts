plugins {
    alias(libs.plugins.tbcacademy.android.feature.api)
}

android {
    namespace = "com.example.core.splash.api"
}

dependencies {
    api(projects.core.navigation)
    implementation(projects.core.domain)
}