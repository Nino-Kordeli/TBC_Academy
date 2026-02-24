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
include(":core:designsystem")
include(":core:ui")
include(":core:domain")
include(":core:data")
include(":core:common")

// Feature
include(":feature:authentication:api")
include(":feature:authentication:impl")

include(":feature:quiz:api")
include(":feature:quiz:impl")

include(":feature:dashboard:api")
include(":feature:dashboard:impl")

include(":feature:add_food:api")
include(":feature:add_food:impl")

include(":feature:diary:impl")
include(":feature:diary:api")
include(":core:model")

include(":feature:add_food_details:api")
include(":feature:add_food_details:impl")

include(":feature:splash:api")
include(":feature:splash:impl")

include(":feature:profile:api")
include(":feature:profile:impl")

include(":feature:workout:api")
include(":feature:workout:impl")

include(":feature:recipes:api")
include(":feature:recipes:impl")
