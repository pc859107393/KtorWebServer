package acheng1314.cn.utils

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.SerializerProvider
import org.apache.commons.lang3.StringUtils
import org.joda.time.format.DateTimeFormat
import java.io.IOException
import java.util.*

/**
 * jackSon输出时间（@see org.joda.time.DateTime）使用 JsonSerializer 实现序列化输出为：yyyy年MM月dd日 HH:mm:ss
 * @JsonSerialize(using = OtherObjectSerializer::class)
 */
class OtherObjectSerializer : com.fasterxml.jackson.databind.JsonSerializer<Any>() {


    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: Any?, gen: JsonGenerator, serializers: SerializerProvider?) {
        //时间序列化输出
        if (null != value && value is org.joda.time.DateTime)
            gen.writeString(value.toString("yyyy-MM-dd HH:mm:ss", Locale.CHINESE))
    }


}

/**
 * 反序列化的时间
 * @JsonDeserialize(using = OtherObjectDeserializer::class)
 */
class OtherObjectDeserializer : com.fasterxml.jackson.databind.JsonDeserializer<Any>() {

    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Any? {
        return try {
            if (p != null && StringUtils.isNotBlank(p.text)) {
                org.joda.time.DateTime.parse(p.text, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.CHINESE))
            } else null
        } catch (e: Exception) {
            logger.error("序列化时间失败，仅支持格式yyyy-MM-dd HH:mm:ss，错误：${e.message}")
            null
        }
    }

}