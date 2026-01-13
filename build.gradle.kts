buildscript {
    repositories {
        google()
    }
    dependencies {
        val nav_version = "2.9.5"
        classpath("com.google.gms:google-services:4.4.2")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.57.2" apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.28" apply false
}