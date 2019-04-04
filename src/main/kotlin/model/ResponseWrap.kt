package model

import io.ktor.http.HttpStatusCode
import java.io.Serializable

class ResponseWrap : Serializable {
    var code: Int = 200
    var msg: String = "OK"
    var data: Any? = null

    fun warp(data: Any?): ResponseWrap {
        if (null == data) return notFound()
        if (data is HttpStatusCode) return initHttpCode(data)
        if (data is List<*> && data.isEmpty()) return empty()
        this.code = HttpStatusCode.OK.value
        this.msg = HttpStatusCode.OK.description
        this.data = data
        return this
    }

    private fun empty(): ResponseWrap {
        this.code = HttpStatusCode.NoContent.value
        this.msg = HttpStatusCode.NoContent.description
        return this
    }

    private fun notFound(): ResponseWrap {
        this.code = HttpStatusCode.NotFound.value
        this.msg = HttpStatusCode.NotFound.description
        return this
    }

    fun initHttpCode(httpCode: HttpStatusCode): ResponseWrap {
        this.code = httpCode.value
        this.msg = httpCode.description
        return this
    }

}