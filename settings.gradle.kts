rootProject.name = "Listify"
include(":composeApp")

pluginManagement {
    repositories {
        google()
//        google {
//            content {
//              	includeGroupByRegex("com\\.android.*")
//              	includeGroupByRegex("com\\.google.*")
//              	includeGroupByRegex("androidx.*")
//              	includeGroupByRegex("android.*")
//            }
//        }
//        gradlePluginPortal()
        mavenCentral()
    }
}
//plugins{
//    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
//}
dependencyResolutionManagement {
    repositories {
        google()
//        google {
//            content {
//              	includeGroupByRegex("com\\.android.*")
//              	includeGroupByRegex("com\\.google.*")
//              	includeGroupByRegex("androidx.*")
//              	includeGroupByRegex("android.*")
//            }
//        }
        mavenCentral()
    }
}
include(":config:development", ":config:production")

