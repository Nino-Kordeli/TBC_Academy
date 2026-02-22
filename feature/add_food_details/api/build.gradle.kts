plugins {
    alias(libs.plugins.tbcacademy.android.feature.api)
}

android {
    namespace = "com.example.core.add_food_details.api"
}

dependencies {
    api(projects.core.navigation)
    api(projects.core.domain)
    implementation(projects.core.domain)
}