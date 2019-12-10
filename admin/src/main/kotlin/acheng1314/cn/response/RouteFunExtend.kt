package acheng1314.cn.response

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.http.HttpMethod
import io.ktor.request.contentType
import io.ktor.request.receive
import io.ktor.routing.Route
import io.ktor.routing.route
import io.ktor.util.pipeline.ContextDsl
import io.ktor.util.pipeline.PipelineContext
import org.apache.commons.lang3.StringUtils

///**
// * Builds a route to match `POST` requests with specified [path] receiving request body content of type [R]
// */
//@ContextDsl
//@JvmName("postTyped")
//inline fun <reified R : Any> Route.jsonPost(
//        path: String,
//        crossinline body: suspend PipelineContext<Unit, ApplicationCall>.(R) -> Unit
//): Route {
//    return route(path, HttpMethod.Post) {
//        handle {
//            body(call.receive())
//            val contentType = this.context.request.headers["Content-Type"]
//            call.application.log.info("请求头为：${contentType}")
//            if (StringUtils.isBlank(contentType) || !contentType!!.contains("application/json")) {
//                throw Exception("please check request ， must be application/json; charset=utf-8 ")
//            }
//        }
//    }
//}