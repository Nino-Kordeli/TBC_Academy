plugins {
    alias(libs.plugins.tbcacademy.android.feature.api)
}

android {
    namespace = "com.example.feature.recipes.api"
}

dependencies {
    api(projects.core.navigation)
    implementation(projects.core.domain)
}