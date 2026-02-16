plugins {
    alias(libs.plugins.tbcacademy.android.library)
    id("com.google.devtools.ksp")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.domain"
}

dependencies {
    //api(projects.core.model)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.javax.inject)
}