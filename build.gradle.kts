plugins {
    jacoco
    id("org.sonarqube") version "3.1.1"
}

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31")
    }
}

subprojects {
    sonarqube {
        properties {
            property("sonar.sources", "src")
            property(
                "sonar.coverage.jacoco.xmlReportPaths",
                file("$buildDir/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
            )
            property("sonar.coverage.exclusions", "src/**Test")
        }
    }
}

sonarqube {
    properties {
        property("sonar.projectKey", "Reproducers_coveralls-repro")
        property("sonar.organization", "reproducers")
        property("sonar.host.url", "https://sonarcloud.io")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            file("**/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
        )
        property("sonar.coverage.exclusions", "src/**Test")
    }
}
