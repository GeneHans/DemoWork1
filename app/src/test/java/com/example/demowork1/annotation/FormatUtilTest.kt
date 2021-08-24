package com.example.demowork1.annotation

import junit.framework.TestCase

import org.junit.Test

import org.junit.Assert.*

class FormatUtilTest : TestCase() {

    @Test
    fun testMain(){
        assertEquals(FormatUtil.instance.isMatch("发"),false)
        assertEquals(FormatUtil.instance.isMatch("发1"),false)
        assertEquals(FormatUtil.instance.isMatch("12"),true)
        assertEquals(FormatUtil.instance.isMatch("    "),false)
        assertEquals(FormatUtil.instance.isMatch(""),false)
        assertEquals(FormatUtil.instance.isMatch("1a1"),false)

    }

}