plugins {
    alias(libs.plugins.tbcacademy.jvm.library)
    alias(libs.plugins.tbcacademy.hilt)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}
