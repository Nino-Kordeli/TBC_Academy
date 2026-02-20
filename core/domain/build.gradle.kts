plugins {
    alias(libs.plugins.tbcacademy.android.library)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.domain"
}

dependencies {
    api(projects.core.model)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)

}