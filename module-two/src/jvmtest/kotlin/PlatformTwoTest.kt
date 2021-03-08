package com.javiersc.coverallsRepro.moduleTwo

import com.javiersc.coverallsRepro.moduleOne.PlatformOne
import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformTwoTest {

    @Test
    fun platformSuccessTest() {
        assertEquals(PlatformOne.name, "JVM")
        assertEquals(PlatformTwo.name, "JVM")
    }
}
