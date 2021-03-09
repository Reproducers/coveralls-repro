plugins {
    jacoco
    id("org.sonarqube") version "3.1.1"
}

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31")
    }
}

val sources: List<File> =
    subprojects
        .map { project ->
            file("${project.projectDir}/src/")
                .walkBottomUp()
                .maxDepth(2)
                .filter { it.name.contains("main") }
                .toSet()
                .toList()
        }
        .flatten()

tasks.register<JacocoMerge>("jacocoMerge") {
    group = "verification"
    subprojects.onEach { subproject -> executionData(subproject.tasks.withType<Test>()) }
    doFirst { executionData = files(executionData.filter { file: File -> file.exists() }) }
}

tasks.register<JacocoReport>("jacocoTestReport") {
    group = "verification"

    dependsOn(subprojects.map { it.tasks.getByName("check") })
    dependsOn("jacocoMerge")

    val classes =
        subprojects.flatMap { project ->
            file("${project.buildDir}/classes/kotlin/jvm/main").walkBottomUp().toSet()
        }

    classDirectories.setFrom(classes)
    sourceDirectories.setFrom(files(sources))

    executionData.setFrom(tasks.getByName<JacocoMerge>("jacocoMerge").destinationFile)

    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}

subprojects { sonarqube { properties { property("sonar.sources", "src") } } }

sonarqube {
    properties {
        property("sonar.projectKey", "Reproducers_coveralls-repro")
        property("sonar.organization", "reproducers")
        property("sonar.host.url", "https://sonarcloud.io")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"
        )
    }
}
