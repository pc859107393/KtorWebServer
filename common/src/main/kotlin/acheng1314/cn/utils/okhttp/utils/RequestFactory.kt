package acheng1314.cn.utils.okhttp.utils

import acheng1314.cn.utils.okhttp.enums.HttpMethod
import acheng1314.cn.utils.okhttp.model.HttpParam
import acheng1314.cn.utils.okhttp.wrapper.GetRequestWrapper
import acheng1314.cn.utils.okhttp.wrapper.PostRequestWrapper
import acheng1314.cn.utils.okhttp.wrapper.RequestWrapper
import okhttp3.Request

/**
 * 请求构造的工厂
 *
 * @author cheng
 */
class RequestFactory
/**
 * 根据这个工厂生成对应okhttp的请求
 *
 * @param method http请求方法
 * @param params http请求参数
 * @param url    http请求url
 */
(private val method: HttpMethod, private val params: HttpParam, private val url: String) {

    /**
     * 根据请求内容型实现请求体生成
     *
     * @return
     */
    fun initRequest(): Request? {
        var wrapper: RequestWrapper? = null
        when (method) {
            HttpMethod.GET -> {
                wrapper = GetRequestWrapper(url, params)
            }
            HttpMethod.POST -> {
                wrapper = PostRequestWrapper(url, params)
            }
            else -> {
                //                throw new RuntimeException("暂未实现该方法");
                return null
            }
        }
        return wrapper.create()
    }

}

