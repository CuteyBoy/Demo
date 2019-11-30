package com.lsj.demo.encrypt

import android.util.Base64
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.RSAKeyGenParameterSpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

/**
 * @CreateDate: 2019-11-30
 * @Version: 1.0.0
 */
object RSAUtils {

    /**
     * 生成RSA加密密匙对，返回public和private密匙对字符串
     * 第一个是publicKey, 第二是privateKey
     */
    fun generatorKeyPair(): Pair<String, String> {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4))
        val keyPair = keyPairGenerator.genKeyPair()
        val publicKey = Base64.encodeToString(keyPair.public.encoded,  Base64.DEFAULT)
        val privateKey = Base64.encodeToString(keyPair.private.encoded, Base64.DEFAULT)
        return Pair(publicKey, privateKey)
    }

    /**
     * 使用rsa提供的公共的密钥进行加密
     * @param encodeText 需要加密的文本
     * @param publicKey 公共密钥
     * @return 返回加密后文本
     */
    fun encode(encodeText: String, publicKey: String): String {
        val publicKeyDecoded = Base64.decode(publicKey, Base64.DEFAULT)
        val rsaPublicKey = KeyFactory.getInstance("RSA")
            .generatePublic(X509EncodedKeySpec(publicKeyDecoded))
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey)
        return Base64.encodeToString(cipher.doFinal(encodeText.toByteArray()), Base64.DEFAULT)
    }

    /**
     * 使用rsa提供的私有的密钥进行解密
     * @param decodedText 需要解密的文本
     * @param privateKey 私密密钥
     * @return 返回需要解密的文本
     */
    fun decoded(decodedText: String, privateKey: String): String{
        val decodedTextByte = Base64.decode(decodedText.toByteArray(), Base64.DEFAULT)
        val decodedPrivateKey = Base64.decode(privateKey, Base64.DEFAULT)
        val rsaPrivateKey = KeyFactory.getInstance("RSA")
            .generatePrivate(PKCS8EncodedKeySpec(decodedPrivateKey))
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey)
        return String(cipher.doFinal(decodedTextByte), Charsets.UTF_8)
    }
}