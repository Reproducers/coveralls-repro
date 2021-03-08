package com.javiersc.coverallsRepro.moduleTwo

import com.javiersc.coverallsRepro.moduleOne.CommonOne
import kotlin.test.Test
import kotlin.test.assertEquals

class CommonTwoTest {

    @Test
    fun commonTwoSuccessTest() {
        assertEquals(CommonOne.name, "one")
        assertEquals(CommonTwo.name, "two")
    }
}
