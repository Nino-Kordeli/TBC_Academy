plugins {
    alias(libs.plugins.tbcacademy.android.feature.impl)
    alias(libs.plugins.tbcacademy.android.library.compose)
}

android {
    namespace = "com.example.add_food.impl"
    resourcePrefix = "add_food"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.feature.addFood.api)
    implementation(libs.androidx.activity.compose)
    implementation(projects.feature.addFoodDetails.api)
}