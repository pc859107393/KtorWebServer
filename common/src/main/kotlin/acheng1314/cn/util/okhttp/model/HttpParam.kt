package acheng1314.cn.util.okhttp.model

import com.alibaba.fastjson.JSONObject

import java.io.*
import java.util.*

/**
 * Http请求的参数集合
 *
 * @author cheng
 */
class HttpParam : Serializable {
    private var mBoundary: String? = generateBoundary()

    private val paramsEntries = HashMap<String, Any>(8)
    val headers = ArrayList<HttpParamsEntry>(4)

    private val mOutputStream = ByteArrayOutputStream()
    var isHasFile: Boolean = false
        private set
    private val contentType: String? = null

    var isJson: Boolean = false

    val params: StringBuilder
        get() {
            val result = StringBuilder()
            var isFirst = true

            for ((key, value) in paramsEntries) {
                if (value is File) {
                    continue
                }
                if (!isFirst) {
                    result.append("&")
                } else {
                    isFirst = false
                }
                result.append(key).append("=").append(value)
            }

            return result
        }

    /**
     * 直接返回请求的内容的json对象
     *
     * @return
     */
    val jsonBody: String?
        get() = if (isJson) {
            JSONObject.toJSONString(paramsEntries)
        } else {
            null
        }


    /**
     * 生成分隔符
     */
    private fun generateBoundary(): String {
        val buf = StringBuilder()
        val rand = Random()
        for (i in 0..29) {
            buf.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.size)])
        }
        return buf.toString()
    }

    fun putHeaders(key: String, value: String) {
        headers.add(HttpParamsEntry(key, value))
    }

    fun put(key: String, value: Int) {
        this.put(key, value.toString() + "")
    }


    /**
     * 添加文本参数
     */
    fun put(key: String, value: String) {
        paramsEntries[key] = value
    }


    /**
     * 添加文件参数,可以实现文件上传功能
     */
    fun put(key: String, file: File) {
        isHasFile = true
        paramsEntries[key] = file
    }

    fun getParamsEntries(): Map<String, Any> {
        return paramsEntries
    }

    fun removeParam(key: String) {
        paramsEntries.remove(key)
    }

    fun clearParams() {
        paramsEntries.clear()
    }

    companion object {

        private val MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
        private val NEW_LINE_STR = "\r\n"
        private val CONTENT_TYPE = "Content-Type: "
        private val CONTENT_DISPOSITION = "Content-Disposition: "

        val CHARSET = "UTF-8"

        //文本参数和字符集
        private val TYPE_TEXT_CHARSET = String.format("text/plain; charset=%s",
                CHARSET)

        //字节流参数
        private val TYPE_OCTET_STREAM = "application/octet-stream"

        //二进制参数
        private val BINARY_ENCODING = "Content-Transfer-Encoding: binary\r\n\r\n"
                .toByteArray()
        // 文本参数
        private val BIT_ENCODING = "Content-Transfer-Encoding: 8bit\r\n\r\n".toByteArray()
    }
}
