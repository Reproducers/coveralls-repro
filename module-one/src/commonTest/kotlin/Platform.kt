package com.javiersc.coverallsRepro.moduleOne

import kotlin.test.Test

class PlatformTest {

    @Test
    fun platformTest() {
        assert(Platform.name == "JVM")
    }
}
