package com.javiersc.coverallsRepro.moduleOne

import kotlin.test.Test
import kotlin.test.assertEquals

class CommonOneTest {

    @Test
    fun commonOneSuccessTest() {
        assertEquals(CommonOne.name, "one")
    }
}
