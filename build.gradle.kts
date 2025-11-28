buildscript {
    repositories {
        google()
    }
    dependencies {
        val nav_version = "2.9.5"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.9.4")
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.57.2" apply false
}