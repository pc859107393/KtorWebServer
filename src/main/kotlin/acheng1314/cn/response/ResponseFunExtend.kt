package acheng1314.cn.response

import io.ktor.application.ApplicationCall
import io.ktor.http.ContentType
import acheng1314.cn.model.ResponseWrap
import io.ktor.response.defaultTextContentType


@Suppress("NOTHING_TO_INLINE")
suspend inline fun ApplicationCall.respondJson(data: Any) {
    this.defaultTextContentType(ContentType.Application.Json)
    response.pipeline.execute(this, ResponseWrap().warp(data))
}

@Suppress("NOTHING_TO_INLINE")
suspend inline fun ApplicationCall.respondJson(data: Any, msg: String) {
    this.defaultTextContentType(ContentType.Application.Json)
    response.pipeline.execute(this, ResponseWrap().warp(data, msg))
}

@Suppress("NOTHING_TO_INLINE")
suspend inline fun ApplicationCall.respondJson(data: Any, pageNum: Int, pageSize: Int) {
    this.defaultTextContentType(ContentType.Application.Json)
    response.pipeline.execute(this, ResponseWrap().warp(data).page(pageNum, pageSize))
}
