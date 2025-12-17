buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        val nav_version = "2.9.5"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.0")
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.57.2" apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.28" apply false
    alias(libs.plugins.google.gms.google.services) apply false
}