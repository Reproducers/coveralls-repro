plugins {
    kotlin("multiplatform")
    jacoco
}

kotlin {
    jvm()
    js(BOTH) {
        nodejs()
    }

    sourceSets {
        commonTest {
            dependencies { implementation("org.jetbrains.kotlin:kotlin-test-multiplatform") }
        }
        named("jvmMain")
        named("jvmTest")
        named("jsMain")
        named("jsTest")
    }
}


tasks.register<JacocoReport>("jacocoTestReport") {
    group = "verification"

    dependsOn("check")

    val sources: List<File> =
        file("$projectDir/src/")
            .walkBottomUp()
            .maxDepth(2)
            .filter { it.path.contains("kotlin", ignoreCase = true) }
            .filter { it.path.contains("main", ignoreCase = true) }
            .toSet()
            .toList()

    val classes = file("$buildDir/classes/kotlin/jvm/main").walkBottomUp().toSet()

    classDirectories.setFrom(classes)
    sourceDirectories.setFrom(files(sources))

    executionData.setFrom("$buildDir/jacoco/jvmTest.exec")

    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}
