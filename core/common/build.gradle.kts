plugins {
    alias(libs.plugins.tbcacademy.jvm.library)
    alias(libs.plugins.tbcacademy.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}
