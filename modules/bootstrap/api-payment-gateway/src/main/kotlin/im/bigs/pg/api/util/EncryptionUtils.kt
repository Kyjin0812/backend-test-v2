package im.bigs.pg.api.util

import java.security.MessageDigest
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtils {
    private const val ALGORITHM = "AES/GCM/NoPadding"
    private const val TAG_LENGTH = 128

    /**
     * 평문 JSON을 AES-256-GCM으로 암호화하여 Base64URL 형식으로 반환합니다.
     */
    fun encrypt(plainText: String, apiKey: String, ivB64Url: String): String {
        // 1. Key 생성: SHA-256(API-KEY) -> 32바이트
        val digest = MessageDigest.getInstance("SHA-256")
        val keyBytes = digest.digest(apiKey.toByteArray(Charsets.UTF_8))
        val secretKey = SecretKeySpec(keyBytes, "AES")

        // 2. IV 준비: Base64URL 디코딩 (12바이트)
        val ivBytes = Base64.getUrlDecoder().decode(ivB64Url)

        // 3. Cipher 설정 및 암호화
        val cipher = Cipher.getInstance(ALGORITHM)
        val spec = GCMParameterSpec(TAG_LENGTH, ivBytes)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec)

        // ciphertext + tag 합쳐진 결과물 생성
        val encryptedBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

        // 4. Base64URL 인코딩 (패딩 없음)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(encryptedBytes)
    }
}
