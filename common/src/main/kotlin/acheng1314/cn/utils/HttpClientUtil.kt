package acheng1314.cn.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.content.TextContent
import io.ktor.http.*
import io.ktor.http.content.OutgoingContent
import io.ktor.util.InternalAPI
import io.ktor.util.flattenEntries
import kotlinx.coroutines.async
import kotlinx.coroutines.io.ByteWriteChannel
import kotlinx.coroutines.io.writeFully
import kotlinx.coroutines.io.writeStringUtf8
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.*
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableMap
import kotlin.collections.arrayListOf
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.forEach
import kotlin.collections.plusAssign
import kotlin.collections.set
import kotlin.collections.toList

/**
 * httpClient工具类
 * @author cheng
 */
object HttpClientUtil {

    val httpClient: HttpClient = HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
    }

    /**
     * post方式上传json数据，返回String
     */
    @JvmOverloads
    suspend inline fun <reified T> jsonPost(
            url: String,
            content: String? = null,
            header: Map<String, String>? = null
    ): T =
            httpClient.post<T>(url) {
                header?.forEach { k, v ->
                    this.headers[k] = v
                }

                content?.run {
                    this@post.body = TextContent(
                            this, contentType = ContentType.Application.Json
                    )
                }
            }

    @JvmOverloads
    suspend inline fun <reified T> kvPost(
            url: String,
            content: Map<String, Any>? = null,
            header: Map<String, String>? = null
    ): T =
            httpClient.post<T>(url) {
                header?.forEach { k, v ->
                    this.headers[k] = v
                }
                content?.run {
                    this@post.body = TextContent(
                            mapToQueryString(this)
                            , contentType = ContentType.Application.FormUrlEncoded
                    )
                }
            }

    @JvmOverloads
    suspend inline fun <reified T> filePost(
            url: String,
            content: Map<String, Any>? = null,
            header: Map<String, String>? = null
    ): T =
            httpClient.post<T>(url) {
                header?.forEach { k, v ->
                    this.headers[k] = v
                }

                content?.run {
                    var isFile = false
                    values.forEach {
                        if (it is File) {
                            isFile = true
                        }
                    }

                    if (!isFile) throw Exception("do not found file！")

                    this@post.body = MultiPartContent.build {
                        this@run.forEach { (k, v) ->
                            if (v is File) {
                                add(k, (v).readBytes(), null, k)
                            } else {
                                add(k, v.toString())
                            }
                        }
                    }
                }
            }

    @JvmOverloads
    suspend inline fun <reified T> get(
            url: String,
            content: Map<String, Any>? = null,
            header: Map<String, String>? = null
    ): T =
            httpClient.get<T>(String.format("%s?%s", url, content?.let { mapToQueryString(it) })) {
                header?.forEach { k, v ->
                    this.headers[k] = v
                }
            }

    @JvmStatic
    fun mapToQueryString(map: Map<String, Any>): String {
        var isFirst = true
        val sb = StringBuilder()
        map.forEach { (k, v) ->
            if (!isFirst) sb.append("&")
            sb.append(k).append("=").append(v.toString())
            if (isFirst) isFirst = false
        }
        return sb.toString()
    }

    /**
     * 文件上传专用
     */
    class MultiPartContent(private val parts: List<Part>) : OutgoingContent.WriteChannelContent() {
        private val uuid = UUID.randomUUID()
        private val boundary = "***ktor-$uuid-ktor-${System.currentTimeMillis()}***"

        data class Part(val name: String, val filename: String? = null, val headers: Headers = Headers.Empty, val writer: suspend ByteWriteChannel.() -> Unit)

        override suspend fun writeTo(channel: ByteWriteChannel) {
            for (part in parts) {
                channel.writeStringUtf8("--$boundary\r\n")
                val partHeaders = Headers.build {
                    val fileNamePart = if (part.filename != null) "; filename=\"${part.filename}\"" else ""
                    append("Content-Disposition", "form-data; name=\"${part.name}\"$fileNamePart")
                    appendAll(part.headers)
                }
                for ((key, value) in partHeaders.flattenEntries()) {
                    channel.writeStringUtf8("$key: $value\r\n")
                }
                channel.writeStringUtf8("\r\n")
                part.writer(channel)
                channel.writeStringUtf8("\r\n")
            }
            channel.writeStringUtf8("--$boundary--\r\n")
        }

        override val contentType = ContentType.MultiPart.FormData
                .withParameter("boundary", boundary)
                .withCharset(Charsets.UTF_8)

        class Builder {
            private val parts = arrayListOf<Part>()

            private fun add(part: Part) {
                parts += part
            }

            @UseExperimental(InternalAPI::class)
            private fun add(name: String, filename: String? = null, contentType: ContentType? = null, header: MutableMap<String, String>? = null, writer: suspend ByteWriteChannel.() -> Unit) {
                val headers: Headers
                if (null != header) {
                    contentType?.run {
                        header[HttpHeaders.ContentType] = contentType.contentType
                    }
                    headers = Headers.build {
                        header.forEach { (t, u) -> this.append(t, u) }
                    }

                } else {
                    headers = if (contentType != null) headersOf(HttpHeaders.ContentType, contentType.toString()) else headersOf()
                }

                add(Part(name, filename, headers, writer))
            }

            fun add(name: String, text: String, contentType: ContentType? = null, filename: String? = null) {
                add(name, filename, contentType) { writeStringUtf8(text) }
            }

            fun add(name: String, data: ByteArray, contentType: ContentType? = ContentType.Application.OctetStream, filename: String? = null) {
                add(name, filename, contentType) { writeFully(data) }
            }

            internal fun build(): MultiPartContent = MultiPartContent(parts.toList())
        }

        companion object {
            fun build(callback: Builder.() -> Unit) = Builder().apply(callback).build()
        }
    }


}

fun main(args: Array<String>) = runBlocking<Unit> {

    //    val async = async {
//        val url = Url("http://localhost:8181/admin/loginDef")
//
//        val map = HashMap<String, String>()
//
//        map["userName"] = "pc859107393"
//        map["pwd"] = "9945685a392931fc746b0c5023427481"
//
//        return@async HttpClientUtil.kvPost<String>(url.toString(), map)
//    }

    val async = async {
        val url = Url("https://www.acheng1314.cn/manage/uploadFile")

        val map = HashMap<String, Any>()
        map["file"] = File("/Users/chengpang/IdeaProjects/KtorWebServer/admin/build/libs/application-pro.conf")

        return@async HttpClientUtil.filePost<String>(url.toString(), map)
    }

    async.await().run {
        println(this)
    }

    async.cancel()

}