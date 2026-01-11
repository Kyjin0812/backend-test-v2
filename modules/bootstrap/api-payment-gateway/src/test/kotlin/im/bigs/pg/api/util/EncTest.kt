package im.bigs.pg.api.util

import org.junit.jupiter.api.Test

class EncTest {
    @Test
    fun generateEnc() {
        val plainText = """{"cardNumber":"1111-1111-1111-1111","birthDate":"19900101","expiry":"1227","password":"12","amount":10000}"""
        val apiKey = "11111111-1111-4111-8111-111111111111"
        val iv = "AAAAAAAAAAAAAAAA"

        val enc = EncryptionUtils.encrypt(plainText, apiKey, iv)

        println("\n================ RESULT ================")
        println("Generated enc: $enc")
        println("========================================\n")
    }
}
