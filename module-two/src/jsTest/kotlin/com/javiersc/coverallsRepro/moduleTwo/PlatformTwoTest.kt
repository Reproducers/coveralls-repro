package com.javiersc.coverallsRepro.moduleTwo

import com.javiersc.coverallsRepro.moduleOne.PlatformOne
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PlatformTwoTest {

    @Test
    fun platformSuccessTest() {
        assertEquals(PlatformOne.name, "JS")
        assertEquals(PlatformTwo.name, "JS")
    }
}
