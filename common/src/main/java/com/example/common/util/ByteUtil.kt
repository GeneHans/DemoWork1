package com.example.common.util

object ByteUtil {

    private val HEX_CHAR =
        charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

    /**
     * 将byte[]转换为16进制字符串
     */
    fun bytesToHex(bytes: ByteArray): String {
        //一个byte为8位，可用两个十六进制位标识
        val buf = CharArray(bytes.size * 2)
        var a = 0
        var index = 0
        for (b in bytes) { // 使用除与取余进行转换
            a = if (b < 0) {
                256 + b
            } else {
                b.toInt()
            }
            buf[index++] = HEX_CHAR[a / 16]
            buf[index++] = HEX_CHAR[a % 16]
        }
        return String(buf)
    }

    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str
     * @return
     */
    fun hexToBytes(str: String?): ByteArray {
        if (str == null || str.trim { it <= ' ' } == "") {
            return ByteArray(0)
        }
        val bytes = ByteArray(str.length / 2)
        for (i in 0 until str.length / 2) {
            val subStr = str.substring(i * 2, i * 2 + 2)
            bytes[i] = subStr.toInt(16).toByte()
        }
        return bytes
    }
}