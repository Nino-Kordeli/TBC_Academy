plugins {
    alias(libs.plugins.tbcacademy.android.feature.api)
}

android {
    namespace = "com.example.core.add_food.api"
}

dependencies {
    api(projects.core.navigation)
}