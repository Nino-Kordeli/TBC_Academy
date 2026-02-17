import org.gradle.kotlin.dsl.invoke

plugins {
    alias(libs.plugins.tbcacademy.android.library)
    alias(libs.plugins.tbcacademy.hilt)
    alias(libs.plugins.kotlin.serialization)
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

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(platform(libs.firebase.bom))
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.firebase.auth)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.common)
    implementation(libs.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)
}