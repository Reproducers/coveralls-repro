package com.javiersc.coverallsRepro.moduleOne

import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformOneTest {

    @Test
    fun platformSuccessTest() {
        assertEquals(PlatformOne.name, "JS")
    }
}
