plugins {
    alias(libs.plugins.tbcacademy.android.feature.api)
}

android {
    namespace = "com.example.feature.profile.api"
}

dependencies {
    api(projects.core.navigation)

}