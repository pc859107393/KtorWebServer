package acheng1314.cn.util.okhttp.utils

import com.alibaba.fastjson.util.IOUtils.UTF8
import okhttp3.Interceptor
import okio.Buffer
import org.slf4j.LoggerFactory
import java.io.IOException
import java.nio.charset.Charset

/**
 * 日志拦截
 *
 * @author cheng
 */
class LogInterceptor : Interceptor {

    internal var log = LoggerFactory.getLogger(LogInterceptor::class.java)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val builder = StringBuilder()
        builder.append("|=============开始===============|\n")
        val request = chain.request()
        val startTime = System.currentTimeMillis()
        val response = chain.proceed(chain.request())
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        val mediaType = response.body()?.contentType()
        val content = response.body()?.string()!!
        try {
            builder.append(String.format("request--->content:%s\n", request.toString()))
            builder.append(String.format("request--->headers:%s\n", request.headers().toString()))
            if (null != request.body() && null != request.body()?.contentType()) {
                builder.append(String.format("request--->contentType:%s\n", request.body()?.contentType()!!.toString()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val method = request.method()
        if ("POST" == method) {
            if (null != request.body()) {
                val requestBody = request.body()
                if (null != requestBody) {
                    val body: String?
                    val buffer = Buffer()
                    requestBody.writeTo(buffer)
                    var charset: Charset? = UTF8
                    val contentType = requestBody.contentType()
                    if (contentType != null) {
                        charset = contentType.charset(UTF8)
                    }
                    assert(charset != null)
                    body = buffer.readString(charset!!)
                    builder.append(String.format("request--->params:%s\n", body))
                }
            }
        }
        builder.append(String.format("response--->content:%s\n", content))
        builder.append(String.format("response--->over time:%s毫秒\n", duration))
        builder.append("|=============结束===============|")
        log.info(builder.toString())
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build()
    }
}