plugins {
    kotlin("multiplatform") version "1.4.31"
    jacoco
    id("com.github.nbaztec.coveralls-jacoco") version "1.2.11"
}

kotlin {
    jvm()

    sourceSets {
        commonTest {
            dependencies { implementation("org.jetbrains.kotlin:kotlin-test-multiplatform") }
        }
        named("jvmMain")
    }
}

tasks.register<JacocoReport>("jacocoTestReport") {
    group = "verification"

    dependsOn("check")

    val sources = kotlin.sourceSets.flatMap { it.kotlin.srcDirs }
    val classes = file("${buildDir}/classes/kotlin/jvm/main")

    classDirectories.setFrom(classes)
    sourceDirectories.setFrom(files(sources))
    executionData.setFrom("$buildDir/jacoco/jvmTest.exec")

    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}

coverallsJacoco {
    reportPath = "$buildDir/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"

    reportSourceSets = kotlin.sourceSets.flatMap { it.kotlin.srcDirs }
}
