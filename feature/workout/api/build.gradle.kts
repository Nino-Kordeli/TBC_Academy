plugins {
    alias(libs.plugins.tbcacademy.android.feature.api)
}

android {
    namespace = "com.example.feature.workout.api"
}

dependencies {
    api(projects.core.navigation)
    implementation(projects.core.domain)

}