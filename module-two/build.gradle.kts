plugins {
    kotlin("multiplatform")
    jacoco
}

kotlin {
    jvm()
    js(BOTH) { nodejs() }

    sourceSets {
        commonMain { dependencies { implementation(project(":module-one")) } }

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

    classDirectories.setFrom(file("$buildDir/classes/kotlin/jvm/main").walkBottomUp().toSet())
    sourceDirectories.setFrom(files(projectDir))

    executionData.setFrom(
        fileTree(project.projectDir) { setIncludes(setOf("**/**/*.exec", "**/**/*.ec")) }
    )

    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}
