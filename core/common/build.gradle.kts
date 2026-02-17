plugins {
    alias(libs.plugins.tbcacademy.jvm.library)
    alias(libs.plugins.tbcacademy.hilt)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    implementation(libs.kotlinx.serialization.json)
}
