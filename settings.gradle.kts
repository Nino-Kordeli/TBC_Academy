pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
    }
}

rootProject.name = "tbcacademy"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")

// Core
include(":core:navigation")

// Feature
include(":feature:authentication")
include(":feature:authentication:api")
include(":feature:authentication:impl")
include(":core:designsystem")
include(":core:ui")
include(":core:domain")
include(":core:data")
include(":core:common")
