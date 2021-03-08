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