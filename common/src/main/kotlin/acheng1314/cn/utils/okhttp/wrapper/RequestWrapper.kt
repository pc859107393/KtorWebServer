package acheng1314.cn.utils.okhttp.wrapper

import acheng1314.cn.utils.okhttp.model.HttpParam
import okhttp3.Headers
import okhttp3.Request


/**
 * @author cheng
 */
interface RequestWrapper {

    /**
     * 默认的请求头构造器
     *
     * @param params
     * @return
     */
    fun initHeaders(params: HttpParam): Headers {
        // 创建Headers
        val headers = params.headers
        val headerBuilder = Headers.Builder()
        for (entry in headers) {
            headerBuilder.add(entry.k!!, entry.v.toString())
        }
        return headerBuilder.build()
    }

    /**
     * 请求构造
     *
     * @return
     */
    fun create(): Request
}