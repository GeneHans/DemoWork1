package com.example.demowork1.util

import junit.framework.TestCase
import org.junit.Test

class DateUtilTest : TestCase() {

    @Test
    fun testIsValidDate() {
        assertEquals(DateUtil.instance.isValidDate("2021-31-12 11:12:13"),false)
        assertEquals(DateUtil.instance.isValidDate("2021-11-12 11:12:13"),true)
    }

}