plugins {
    jacoco
    id("com.github.nbaztec.coveralls-jacoco") version "1.2.11"
    id("org.sonarqube") version "3.1.1"
}

buildscript { dependencies { classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31") } }

val sources: List<File> =
    subprojects
        .flatMap { project ->
            file("${project.projectDir}/src/")
                .walkBottomUp()
                .maxDepth(2)
                .filter { it.path.contains("kotlin", ignoreCase = true) }
                .filter { it.path.contains("main", ignoreCase = true) }
                .toSet()
                .toList()
        }

tasks.register<JacocoReport>("jacocoRootReport") {
    group = "verification"

    val classes = subprojects.flatMap { project ->
        file("${project.buildDir}/classes/kotlin/jvm/main").walkBottomUp().toSet()
    }

    val executionDataList = subprojects.flatMap { project ->
        files(project.tasks.named<JacocoReport>("jacocoTestReport").get().executionData)
    }

    sourceDirectories.setFrom(files(sources))
    classDirectories.setFrom(classes)
    executionData.setFrom(executionDataList)

    reports {
        html.isEnabled = true
        xml.isEnabled = true
    }
}

subprojects {
    sonarqube {
        properties {
            property("sonar.sources", "src/commonMain,src/jvmMain")
            property(
                "sonar.coverage.jacoco.xmlReportPaths",
                file("$buildDir/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
            )
        }
    }
}

sonarqube {
    properties {
        property("sonar.projectKey", "Reproducers_coveralls-repro")
        property("sonar.organization", "reproducers")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

coverallsJacoco {
    reportPath = "$buildDir/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"
    reportSourceSets = sources
    dryRun = true
}
