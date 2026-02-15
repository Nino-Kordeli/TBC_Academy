import org.gradle.kotlin.dsl.invoke

plugins {
    alias(libs.plugins.tbcacademy.android.library)
    alias(libs.plugins.tbcacademy.hilt)
    id("kotlinx-serialization")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.data"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    api(projects.core.common)
    api(projects.core.domain)

    implementation(libs.kotlinx.coroutines.android)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    //api(projects.core.datastore)
}