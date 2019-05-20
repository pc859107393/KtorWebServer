package utils

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.sun.istack.internal.NotNull
import org.apache.commons.lang3.StringUtils
import java.util.*

object JSONUtil {

    private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)

    /**
     * toJSONStr:对象序列化成json串
     *
     * @param object
     * @return
     */
    fun toJSONStr(`object`: Any?): String? {
        if (`object` != null) {
            try {
                return JSON.toJSONString(`object`)
            } catch (e: Exception) {
                log.error(e.message)
            }
        }
        return null
    }

    /**
     * json串序列化成对象
     *
     * @param string
     * @param objClass
     * @param <T>
     * @return
    </T> */
    fun <T> toObject(string: String, objClass: Class<T>): T? {
        if (StringUtils.isNotBlank(string)) {
            try {
                val tmp = string.trim { it <= ' ' }
                return JSON.parseObject(tmp, objClass)
            } catch (e: Exception) {
                log.error(e.message)
            }

        }
        return null
    }

    /**
     * json串序列化成对象集合
     *
     * @param string
     * @param objClass
     * @param <T>
     * @return
    </T> */
    fun <T> toArray(string: String, objClass: Class<T>): List<T>? {
        if (StringUtils.isNotBlank(string)) {
            try {
                return JSON.parseArray(string, objClass)
            } catch (e: Exception) {
                log.error(e.message)
            }
        }
        return null
    }

    /**
     * 在json对象中查找某个key的value
     *
     * @param json
     * @param key
     * @return
     */
    @NotNull
    fun getValueFromJson(@NotNull json: String, @NotNull key: String): String {
        return JSONObject.parseObject(json).getString(key) ?: ""
    }

    fun JsonToMap(str: String): SortedMap<String, String> {
        val data = TreeMap<String, String>()
        // 将json字符串转换成jsonObject
        val jsonObject = JSONObject.parseObject(str)
        val entries = jsonObject.entries

        for (entry in entries) {
            data[entry.key] = entry.value.toString()
        }
        return data
    }


}