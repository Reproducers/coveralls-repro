plugins {
    jacoco
    id("com.github.nbaztec.coveralls-jacoco") version "1.2.11"
}

val sources: List<File> =
    subprojects
        .map { project -> file("${project.projectDir}/src/").walkBottomUp().toSet().toList() }
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

tasks.named("coverallsJacoco") { dependsOn("jacocoTestReport") }

coverallsJacoco {
    reportPath = "${rootProject.buildDir}/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"

    reportSourceSets = sources
}
