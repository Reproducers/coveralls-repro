package com.javiersc.coverallsRepro.moduleOne

import kotlin.test.Test
import kotlin.test.assertEquals

internal class CommonOneTest {

    @Test
    fun commonOneSuccessTest() {
        assertEquals(CommonOne.name, "one")
    }
}
