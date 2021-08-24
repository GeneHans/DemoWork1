package com.example.demowork1.annotation

import java.util.regex.Pattern

class FormatUtil {
    private val regEx: String = "^[0-9]*\$"

    fun isMatch(data: String): Boolean {
        if (data.isNullOrEmpty()) {
            return false
        }
        return Pattern.matches(regEx, data)
    }

    fun isMatch(regex: String, string: String?): Boolean {
        if (string.isNullOrEmpty()) {
            return false
        }
        return Pattern.matches(regex, string)
    }

    companion object {
        val instance by lazy { FormatUtil() }
    }
}