package acheng1314.cn.utils.okhttp.wrapper


import acheng1314.cn.utils.okhttp.model.HttpParam
import acheng1314.cn.utils.okhttp.utils.TextUtil
import okhttp3.Request

/**
 * get请求
 *
 * @author cheng
 */
class GetRequestWrapper(url: String, private val params: HttpParam) : RequestWrapper {
    private val url: String

    init {
        var url = url
        val urlParams = params.params
        if (!TextUtil.isEmpty(url) && !TextUtil.isEmpty(urlParams.toString())) {
            url = if (url.contains("?")) {
                "$url&$urlParams"
            } else {
                "$url?$urlParams"
            }
        }

        this.url = url
    }

    override fun create(): Request {
        return Request.Builder()
                .url(url)
                .headers(initHeaders(params))
                .get()
                .build()
    }
}