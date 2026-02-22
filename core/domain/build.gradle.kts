plugins {
    alias(libs.plugins.tbcacademy.android.library)
    id("com.google.devtools.ksp")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.domain"
}

dependencies {
    api(projects.core.model)
    api(projects.core.common)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)
    implementation(libs.kotlinx.serialization.json)
}