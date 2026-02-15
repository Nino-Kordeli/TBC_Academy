plugins {
    alias(libs.plugins.tbcacademy.android.library)
    alias(libs.plugins.tbcacademy.hilt)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.google.core.navigation"
}

dependencies {
    api(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.savedstate.compose)
    implementation(libs.androidx.lifecycle.viewModel.navigation3)
}
