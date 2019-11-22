package acheng1314.cn.util.okhttp.wrapper


import acheng1314.cn.util.okhttp.model.HttpParam
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody

import java.io.File

/**
 * post请求包装
 *
 * @author cheng
 */
class PostRequestWrapper
/**
 * post请求，暂不支持文件请求
 *
 * @param url
 * @param params
 */
(private val url: String, private val params: HttpParam) : RequestWrapper {

    override fun create(): Request {
        return Request.Builder()
                .url(url)
                .headers(initHeaders(params))
                .post(initBody())
                .build()
    }

    private fun initBody(): RequestBody {
        var body: RequestBody? = null
        if (!params.isHasFile) {
            body = if (!params.isJson) {
                RequestBody.create("application/x-www-form-urlencoded".toMediaTypeOrNull(), params.params.toString())
            } else {
                RequestBody.create("application/json".toMediaTypeOrNull(), params.jsonBody!!)
            }
        } else {
            val builder = MultipartBody.Builder()
            //创建文件请求体
            for ((key, value) in params.getParamsEntries()) {
                if (value is File) {
                    builder.addFormDataPart(key, key, RequestBody.create(MultipartBody.FORM, value))
                } else {
                    builder.addFormDataPart(key, value as String)
                }
            }

            body = builder.build()
        }
        return body
    }

}