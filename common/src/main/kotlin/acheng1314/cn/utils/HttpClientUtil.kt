package acheng1314.cn.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.Url
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

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
                this.headers["Content-Type"] = "application/json"

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
        val url = Url("http://localhost:8181/admin/page/1")

        val map = HashMap<String, String>()
        map["token"] = "907509366dbfb3d406afbb7b7a02fab6"

        return@async HttpClientUtil.get<String>(url.toString(), content = null, header = map)
    }

    async.await().run {
        println(this)
    }

    async.cancel()

}