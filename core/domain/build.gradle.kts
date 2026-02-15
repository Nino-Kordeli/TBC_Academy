plugins {
    alias(libs.plugins.tbcacademy.android.library)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.domain"
}

dependencies {
    //api(projects.core.model)

    implementation(libs.javax.inject)
}