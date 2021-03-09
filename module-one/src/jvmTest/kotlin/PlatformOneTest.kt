package com.javiersc.coverallsRepro.moduleOne

import kotlin.test.Test
import kotlin.test.assertEquals

internal class PlatformOneTest {

    @Test
    fun platformSuccessTest() {
        assertEquals(PlatformOne.name, "JVM")
        assertEquals(CoverageOne().yes(), "coverage")
    }
}
