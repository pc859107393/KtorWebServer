package acheng1314.cn.util

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.sun.istack.internal.NotNull
import org.apache.commons.lang3.StringUtils
import java.util.*

object JSONUtil {

    /**
     * toJSONStr:对象序列化成json串
     *
     * @param object
     * @return
     */
    @Throws(Exception::class)
    fun toJSONStr(`object`: Any?): String? =
        if (`object` != null) JSON.toJSONString(`object`) else null

    /**
     * json串序列化成对象
     *
     * @param string
     * @param objClass
     * @param <T>
     * @return
    </T> */
    @Throws(Exception::class)
    fun <T> toObject(string: String, objClass: Class<T>): T? =
        if (StringUtils.isNotBlank(string)) JSON.parseObject(string, objClass) else null

    /**
     * json串序列化成对象集合
     *
     * @param string
     * @param objClass
     * @param <T>
     * @return
    </T> */
    @Throws(Exception::class)
    fun <T> toArray(string: String, objClass: Class<T>): List<T>? =
        if (StringUtils.isNotBlank(string)) JSON.parseArray(string, objClass) else null

    /**
     * 在json对象中查找某个key的value
     *
     * @param json
     * @param key
     * @return
     */
    @NotNull
    @Throws(Exception::class)
    fun getValueFromJson(@NotNull json: String, @NotNull key: String) =
        JSONObject.parseObject(json).getString(key) ?: ""

    fun jsonToMap(str: String): SortedMap<String, String> {
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