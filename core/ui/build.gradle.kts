plugins {
    alias(libs.plugins.tbcacademy.android.library)
    alias(libs.plugins.tbcacademy.android.library.compose)
}

android {
    namespace = "com.example.ui"
}

dependencies {
    api(projects.core.designsystem)
    api(projects.core.model)

    implementation(libs.androidx.browser)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation3.runtime)
}
