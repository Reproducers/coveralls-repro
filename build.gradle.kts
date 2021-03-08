plugins {
    jacoco
    id("com.github.nbaztec.coveralls-jacoco") version "1.2.11"
}


tasks.register<JacocoMerge>("jacocoMerge") {
    group = "verification"
    subprojects.onEach { subproject -> executionData(subproject.tasks.withType<Test>()) }
    doFirst { executionData = files(executionData.filter { it.exists() }) }
}

val sources = subprojects.flatMap { project ->
    listOf(
        file("${project.projectDir}/src/commonMain/kotlin"),
        file("${project.projectDir}/src/jvmMain/kotlin"),
        file("${project.projectDir}/src/androidMain/kotlin")
    )
}

tasks.register<JacocoReport>("jacocoTestReport") {
    group = "verification"

    dependsOn(subprojects.map { it.tasks.getByName("check") })
    dependsOn("jacocoMerge")

    val classes = subprojects.map { project ->
        file("${project.buildDir}/classes/kotlin/jvm/main")
    }

    classDirectories.setFrom(classes)
    sourceDirectories.setFrom(files(sources))

    executionData.setFrom(tasks.getByName<JacocoMerge>("jacocoMerge").destinationFile)

    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}

coverallsJacoco {
    reportPath = "$buildDir/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"

    reportSourceSets = sources
}
