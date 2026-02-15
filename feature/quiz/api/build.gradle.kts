plugins {
    alias(libs.plugins.tbcacademy.android.feature.api)
}

android {
    namespace = "com.example.core.quiz.api"
}

dependencies {
    api(projects.core.navigation)
}