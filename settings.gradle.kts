rootProject.name = "coveralls-repro"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        jcenter()
        gradlePluginPortal()
    }
}

include("module-one")
include("module-two")
