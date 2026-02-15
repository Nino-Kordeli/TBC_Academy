plugins {
    alias(libs.plugins.tbcacademy.android.library)
    alias(libs.plugins.tbcacademy.android.library.compose)
}

android {
    namespace = "com.example.ui"
}

dependencies {
    api(projects.core.designsystem)

    implementation(libs.androidx.browser)
    implementation(libs.coil)
    implementation(libs.coil.compose)
}
