rootProject.name = "coveralls-repro"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        jcenter()
    }
}

include("module-one")
include("module-two")