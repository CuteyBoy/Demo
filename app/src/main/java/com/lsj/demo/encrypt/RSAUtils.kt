package com.lsj.demo.encrypt

import android.util.Base64
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.RSAKeyGenParameterSpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import kotlin.math.ceil

/**
 * @CreateDate: 2019-11-30
 * @Version: 1.0.0
 */
object RSAUtils {

    private const val DEFAULT_RSA_PADDING = "RSA"
    //用于存放公密
    private const val PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/pduEg6kERw7nhPuGdzZdBJdyPjycgU7DM0HM81Eaucl30iXQWJTExNEGQ2rozFe0y3kLIQgpQmDopEe28m7SylsUe/qggs4GaIdZAE4LkB7v4f61uxb8b945sfGR4jUYvxkwKp1Ea6wZO1sL6J2L8A/zl0x8XHJbwfCRmYpqjQIDAQAB"
    //用于存放私密
    private const val PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAL+l24SDqQRHDueE+4Z3Nl0El3I+PJyBTsMzQczzURq5yXfSJdBYlMTE0QZDaujMV7TLeQshCClCYOikR7bybtLKWxR7+qCCzgZoh1kATguQHu/h/rW7Fvxv3jmx8ZHiNRi/GTAqnURrrBk7WwvonYvwD/OXTHxcclvB8JGZimqNAgMBAAECgYAH9tkKBzypYFiWt3iKbH7WW7/9b+et61O+Ge20mjH3Jdxg/dszbtLz4Gf9ViTlusQ0T7Zeh4diy47ibB1M1YNUbbIDktdLX1MFa70ONe/DFDiewp5Te5NrSp4gGBWFoQ6Qipi/9PcebeOQl6aHvG4YaYBumUy3Bbvl8e7An/gNMQJBAPU8QAQBOVZ0SK58nFFql7/niHbqyyHl/8HGb2FSJc63z2HjoOfjp9E/SQ3qMWn4l2agjtgIb3RN1vWdKJ9CwsUCQQDID2+ru1hbjAGdbBNNRoYA10tKc+ys8JycqhOEx64B5NCQLNAZH9fSBk/B/bfcU/HlVOCE3UghKLtVBoPvE+UpAkEA80ZKpu5j5+zBMT7yfx3D3h6cVIOE8WLNfXgRg7TPRqs3Mf9y3rcLAlvKZ+BcFqkB4FAEhDCSCAkz5kF6S8sZlQJBAI47+0b7QUyE87j6V5a/JxJ9/reGR6SExLPVb18doCcUiPuYALN8IjLd7eKcAhYRUI+dkqqjsF5y2Q0QPsjBOlECQQCBNoYY5Ok4jP/feveFux7KEKY6yzidVADxKsw7dPu3LuW4uMDZCXcEexabU13EP4P83VbivlXPREdd0uDrfBZL"

    /**
     * 生成RSA加密密匙对，返回public和private密匙对字符串
     * 第一个是publicKey, 第二是privateKey
     */
    fun generatorKeyPair(): Pair<String, String> {
        val keyPairGenerator = KeyPairGenerator.getInstance(DEFAULT_RSA_PADDING, BouncyCastleProvider())
        keyPairGenerator.initialize(RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4))
        val keyPair = keyPairGenerator.genKeyPair()
        val publicKey = Base64.encodeToString(keyPair.public.encoded,  Base64.DEFAULT)
        val privateKey = Base64.encodeToString(keyPair.private.encoded, Base64.DEFAULT)
        return Pair(publicKey, privateKey)
    }

    /**
     * 使用rsa提供的公共的密钥进行加密
     * @param encodeText 需要加密的文本
     * @return 返回加密后文本
     */
    fun encodeText(encodeText: String): String {
        return encode(encodeText, PUBLIC_KEY)
    }

    /**
     * 使用rsa提供的私有的密钥进行解密
     * @param decodedText 需要解密的文本
     * @return 返回需要解密的文本
     */
    fun decodeText(decodedText: String): String {
        return decoded(decodedText, PRIVATE_KEY)
    }

    /**
     * 使用rsa提供的公共的密钥进行加密
     * @param encodeText 需要加密的文本
     * @param publicKey 公共密钥
     * @return 返回加密后文本
     */
    fun encode(encodeText: String, publicKey: String): String {
        val publicKeyDecoded = Base64.decode(publicKey, Base64.DEFAULT)
        val rsaPublicKey = KeyFactory.getInstance(DEFAULT_RSA_PADDING, BouncyCastleProvider())
            .generatePublic(X509EncodedKeySpec(publicKeyDecoded))
        val cipher = Cipher.getInstance(DEFAULT_RSA_PADDING, BouncyCastleProvider())
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey)
        val encryptArray = encodeText.toByteArray()
        val encryptArraySize = encryptArray.size
        return if(encryptArraySize > cipher.blockSize) {
            val outArray = getOutArray(encryptArray, encryptArraySize, cipher)
            Base64.encodeToString(outArray, Base64.DEFAULT)
        } else {
            Base64.encodeToString(cipher.doFinal(encodeText.toByteArray()), Base64.DEFAULT)
        }
    }

    /**
     * 使用rsa提供的私有的密钥进行解密
     * @param decodedText 需要解密的文本
     * @param privateKey 私密密钥
     * @return 返回需要解密的文本
     */
    fun decoded(decodedText: String, privateKey: String): String{
        val decodedTextByteArray = Base64.decode(decodedText.toByteArray(), Base64.DEFAULT)
        val decodedPrivateKey = Base64.decode(privateKey, Base64.DEFAULT)
        val rsaPrivateKey = KeyFactory.getInstance(DEFAULT_RSA_PADDING, BouncyCastleProvider())
            .generatePrivate(PKCS8EncodedKeySpec(decodedPrivateKey))
        val cipher = Cipher.getInstance(DEFAULT_RSA_PADDING, BouncyCastleProvider())
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey)
        val decodeTextByteSize = decodedTextByteArray.size
        return if(decodeTextByteSize > cipher.blockSize) {
            val outArray = getOutArray(decodedTextByteArray, decodeTextByteSize, cipher)
            String(outArray, Charsets.UTF_8)
        } else {
            String(cipher.doFinal(decodedTextByteArray), Charsets.UTF_8)
        }
    }

    /**
     * DES: 获取分段后的加解密输出字节数组
     **/
    private fun getOutArray(encodeTextArray: ByteArray, encodeTextArraySize: Int, cipher: Cipher): ByteArray {
        val outSize = cipher.getOutputSize(encodeTextArraySize)
        val blockSize = cipher.blockSize
        val forEachSize = ceil((encodeTextArraySize / blockSize).toDouble()).toInt()
        val remainSize = encodeTextArraySize.rem(blockSize)
        val outArray = ByteArray(outSize * forEachSize)
        for(index in 0 until forEachSize) {
            if(index == forEachSize - 1) {
                cipher.doFinal(encodeTextArray, index * blockSize, remainSize, outArray, index * outSize)
            } else {
                cipher.doFinal(encodeTextArray, index * blockSize, blockSize, outArray, index * outSize)
            }
        }
        return outArray
    }
}