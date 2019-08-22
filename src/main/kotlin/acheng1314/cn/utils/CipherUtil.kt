package acheng1314.cn.utils


import kotlinx.io.charsets.Charset
import java.io.UnsupportedEncodingException
import java.security.Key
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

/**
 * 加密与解密的工具类<br></br>
 *
 *
 * **创建时间** 2014-8-14
 *
 * @author kymjs (https://github.com/kymjs)
 * @version 1.1
 */
object CipherUtil {
    /**
     * 16位小写MD5加密
     *
     * @param string
     */
    fun small16md5(string: String): String {
        return small32md5(string).substring(8, 24)
    }


    /**
     * 32位小写MD5加密
     *
     * @log 若要返回16位的直接去掉后面的 substring(8, 24)
     */
    fun small32md5(string: String): String {
        val hash: ByteArray
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.toByteArray(charset("UTF-8")))
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Huh, MD5 should be supported?", e)
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("Huh, UTF-8 should be supported?", e)
        }

        val hex = StringBuilder(hash.size * 2)
        for (b in hash) {
            if ((b.toInt() and 0xFF) < 0x10)
                hex.append("0")
            hex.append(Integer.toHexString(b.toInt() and 0xFF))
        }
        return hex.toString()
    }

    /**
     * 返回可逆算法DES的密钥
     *
     * @param key 前8字节将被用来生成密钥。
     * @return 生成的密钥
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getDESKey(key: ByteArray): Key {
        val des = DESKeySpec(key)
        val keyFactory = SecretKeyFactory.getInstance("DES")
        return keyFactory.generateSecret(des)
    }

    /**
     * 根据指定的密钥及算法，将字符串进行解密。
     *
     * @param data      要进行解密的数据，它是由原来的byte[]数组转化为字符串的结果。
     * @param key       密钥。
     * @param algorithm 算法。
     * @param charset 编码
     * @return 解密后的结果。它由解密后的byte[]重新创建为String对象。如果解密失败，将返回null。
     * @throws Exception
     */
    @Throws(Exception::class)
    fun decrypt(data: String, key: Key, algorithm: String, charset: Charset): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, key)
        return String(cipher.doFinal(hexStringToByteArray(data)), charset)
    }

    /**
     * 根据指定的密钥及算法对指定字符串进行可逆加密。
     *
     * @param data      要进行加密的字符串。
     * @param key       密钥。
     * @param algorithm 算法。
     * @param charset 编码
     * @return 加密后的结果将由byte[]数组转换为16进制表示的数组。如果加密过程失败，将返回null。
     */
    @Throws(Exception::class)
    fun encrypt(data: String, key: Key, algorithm: String, charset: Charset): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return byteArrayToHexString(cipher.doFinal(data
                .toByteArray(charset)))
    }

    /**
     * 16进制表示的字符串转换为字节数组。
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    fun hexStringToByteArray(s: String): ByteArray {
        val len = s.length
        val d = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            d[i / 2] = ((Character.digit(s[i], 16) shl 4) + Character
                    .digit(s[i + 1], 16)).toByte()
            i += 2
        }
        return d
    }

    /**
     * byte[]数组转换为16进制的字符串。
     *
     * @param data 要转换的字节数组。
     * @return 转换后的结果。
     */
    fun byteArrayToHexString(data: ByteArray): String {
        val sb = StringBuilder(data.size * 2)
        for (b in data) {
            val v = b.toInt() and 0xff
            if (v < 16) {
                sb.append('0')
            }
            sb.append(Integer.toHexString(v))
        }
        return sb.toString().toUpperCase(Locale.getDefault())
    }

    fun sha1(str: String): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(str.toByteArray())
        return byteArrayToHexString(result)
    }

    fun sha256(str: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val result = digest.digest(str.toByteArray())
        return byteArrayToHexString(result)
    }

}

fun main(args: Array<String>) {
    val str1 = "1342rwesg2341231"
    println(CipherUtil.small32md5(str1))
    println(CipherUtil.small16md5(str1))
    println(CipherUtil.sha256(str1))
    println(CipherUtil.sha1(str1))
}
