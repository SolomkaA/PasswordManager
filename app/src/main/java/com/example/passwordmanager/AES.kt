package com.example.passwordmanager

import android.os.Build
import java.io.UnsupportedEncodingException
import java.lang.Exception
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class AES {
    private var secretKey: SecretKeySpec? = null
    private lateinit var key: ByteArray


    fun setKey(myKey: String) {
        var sha: MessageDigest? = null
        try {
            key = myKey.toByteArray(charset("UTF-8"))
            sha = MessageDigest.getInstance("SHA-256")
            key = sha.digest(key)
            key = Arrays.copyOf(key, 16)
            secretKey = SecretKeySpec(key, "AES")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }


    fun encrypt(strToEncrypt: String, secret: String): String? {
        try {
            setKey(secret)
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.toByteArray(charset("UTF-8"))))
            }
        } catch (e: Exception) {
            println("Error while encrypting: $e")
        }
        return null
    }


    fun decrypt(strToDecrypt: String?, secret: String): String {
        try {
            setKey(secret)
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)))
            }
        } catch (e: Exception) {
            println("Error while decrypting: $e")
        }
        return ""
    }



}