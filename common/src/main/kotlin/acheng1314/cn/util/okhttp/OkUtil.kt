package acheng1314.cn.util.okhttp

import acheng1314.cn.util.okhttp.enums.HttpMethod
import acheng1314.cn.util.okhttp.model.GzipRequestInterceptor
import acheng1314.cn.util.okhttp.model.HttpParam
import acheng1314.cn.util.okhttp.utils.LogInterceptor
import acheng1314.cn.util.okhttp.utils.RequestFactory
import acheng1314.cn.util.okhttp.utils.SSLUtil
import acheng1314.cn.util.okhttp.utils.TextUtil
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.IOException
import java.util.*

/**
 * okHttp的工具类，建造者模式，枚举<br></br>
 * 使用说明，目前只支持get/post/jsonPost
 *
 *
 * //普通get
 * OkUtil.instance("http://acheng1314.cn/api/v1/findPostsByKey/999GET")
 * .params("pageNum", 1)
 * .params("pageSize", "1")
 * .method(HttpMethod.GET)
 * .execute();
 * //普通post
 * OkUtil.instance("https://acheng1314.cn/api/v1/findPostsByKey/999POST")
 * .params("pageNum", 1)
 * .params("pageSize", "1")
 * .method(HttpMethod.POST)
 * .execute();
 * //json的post提交
 * OkUtil.instance("http://localhost:8181/admin/login")
 * .params("userName", "pc859107393")
 * .params("pwd", "9945685a392931fc746b0c5023427481")
 * .method(HttpMethod.POST)
 * .jsonPost()
 * .execute();
 *
 */
class OkUtil private constructor(private val url: String) {
    private val params = HttpParam()

    private var isGzip = false

    private var method = HttpMethod.GET

    fun method(method: HttpMethod): OkUtil {
        this.method = method
        return this
    }

    fun gzip(isGzip: Boolean): OkUtil {
        this.isGzip = isGzip
        return this
    }

    fun params(key: String, value: String): OkUtil {
        params.put(key, value)
        return this
    }

    fun params(key: String, value: Int): OkUtil {
        params.put(key, value)
        return this
    }

    fun params(key: String, value: Long): OkUtil {
        params.put(key, value.toString() + "")
        return this
    }

    fun params(key: String, value: Boolean): OkUtil {
        params.put(key, value.toString() + "")
        return this
    }

    fun params(key: String, value: Float): OkUtil {
        params.put(key, value.toString() + "")
        return this
    }

    fun params(key: String, value: Double): OkUtil {
        params.put(key, value.toString() + "")
        return this
    }

    fun params(key: String, value: Char): OkUtil {
        params.put(key, value.toInt())
        return this
    }

    /**
     * 开启jsonPost请求
     *
     * @return
     */
    fun jsonPost(): OkUtil {
        params.isJson = java.lang.Boolean.TRUE
        return this
    }

    /**
     * 添加map集合，推荐使用 Map<String></String>,String>>
     *
     * @param map
     * @return
     */
    fun params(map: Map<String, Any>): OkUtil {
        if (map.isNotEmpty()) {
            for ((key, value) in map) {
                params.put(key, value.toString() + "")
            }
        }
        return this
    }

    /**
     * 添加map集合，推荐使用 Map<String></String>,String>>
     *
     * @param map
     * @return
     */
    fun paramsSort(map: SortedMap<String, String>): OkUtil {
        if (!map.isEmpty()) {
            for ((key, value) in map) {
                params.put(key, value)
            }
        }
        return this
    }

    fun params(key: String, value: File): OkUtil {
        params.put(key, value)
        return this
    }

    fun removeParam(key: String) {
        params.removeParam(key)
    }


    fun removeAllParams() {
        params.clearParams()
    }

    /**
     * 建造者模式，添加请求头
     *
     * @param key   key
     * @param value value
     * @return
     */
    fun header(key: String, value: String): OkUtil {
        params.putHeaders(key, value)
        return this
    }

    /**
     * 同步执行，并返回请求响应内容
     *
     * @return
     */
    fun execute(): String? {
        if (TextUtil.isEmpty(url)) {
            throw IllegalArgumentException("Url can not be null!")
        }

        //构造请求
        val request = initRequest()

        var response: Response? = null
        try {
            if (isGzip) {
                response = OkHttpClient.Builder()
                        .addInterceptor(GzipRequestInterceptor())
                        .sslSocketFactory(SSLUtil.createSSLSocketFactory()!!, SSLUtil.TrustAllManager())
                        .hostnameVerifier(SSLUtil.TrustAllHostnameVerifier())
                        .addInterceptor(LogInterceptor())
                        .build()
                        .newCall(request!!)
                        .execute()
            } else {
                response = OkHttpClient.Builder()
                        .sslSocketFactory(SSLUtil.createSSLSocketFactory()!!, SSLUtil.TrustAllManager())
                        .hostnameVerifier(SSLUtil.TrustAllHostnameVerifier())
                        .addInterceptor(LogInterceptor())
                        .build()
                        .newCall(request!!)
                        .execute()
            }
            if (null != response!!.body()) {
                return response.body()?.string()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            response?.close()
        }
        return null
    }

    /**
     * 构造请求，根据请求类型不同，此处应该采用工厂模式
     *
     * @return
     */
    private fun initRequest(): Request? {
        return RequestFactory(method, params, url).initRequest()
    }

    companion object {

        fun instance(url: String): OkUtil {
            return OkUtil(url)
        }
    }

}

fun main(args: Array<String>) {
    var execute = OkUtil.instance("http://localhost:8181/admin/login")
            .method(HttpMethod.POST)
            .params("userName", "pc859107393")
            .params("pwd", "9945685a392931fc746b0c5023427481")
            .jsonPost()
            .execute()
    println(execute)

    execute = OkUtil.instance("https://www.acheng1314.cn/api/v1/findPostsByKey/1")
            .method(HttpMethod.POST)
            .params("pageNum", 1)
            .params("pageSize", 10)
            .execute()
    println(execute)

    execute = OkUtil.instance("https://www.acheng1314.cn/api/v1/findPostsByKey/1?pageNum=1&pageSize=10")
            .method(HttpMethod.GET)
            .execute()
    println(execute)
}
