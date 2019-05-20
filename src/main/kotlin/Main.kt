import com.fasterxml.jackson.databind.SerializationFeature
import database.DatabaseFactory
import exception.NotFoundException
import exception.UnauthorizedException
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.jackson.jackson
import io.ktor.request.uri
import io.ktor.response.respondJson
import io.ktor.routing.Routing
import io.ktor.routing.route
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.KtorExperimentalAPI
import io.ktor.websocket.WebSockets
import log.logger
import org.valiktor.ConstraintViolationException
import org.valiktor.i18n.mapToMessage
import web.admin
import web.widget
import java.util.*

@KtorExperimentalAPI
fun Application.main() {
    val config = environment.config
    //初始化环境变量参数
    val jdbcStr = config.property("dataBase.jdbcUrl").getString()
    val maximumPoolSize = config.property("dataBase.maximumPoolSize").getString()
    val username = config.property("dataBase.username").getString()
    val password = config.property("dataBase.password").getString()
    val isAutoCommit = config.property("dataBase.isAutoCommit").getString()

    //安装插件
    install(DefaultHeaders)
    //安装call
    install(CallLogging)
    //安装webSocket
    install(WebSockets)

    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    //配置跨域
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.AccessControlAllowOrigin)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.AccessControlAllowMethods)
        header("token")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
//        hosts.add()
    }

    //全局异常处理
    install(StatusPages) {
        //数据校验异常捕获输出
        exception<Throwable> { cause ->
            var code = HttpStatusCode.InternalServerError.value
            var msg = cause.message
            when (cause) {
                is NotFoundException -> code = HttpStatusCode.NotFound.value
                is ConstraintViolationException -> {
                    code = HttpStatusCode.Unauthorized.value
                    msg = cause.constraintViolations.mapToMessage(baseName = "messages", locale = Locale.CHINESE)
                            .map { "${it.property}: ${it.message}" }
                            .first()
                }
                is UnauthorizedException -> code = HttpStatusCode.Unauthorized.value
            }
            call.respondJson(HttpStatusCode(code, msg ?: "Other Exception"))
        }
    }

    log.info("dataBase -> jdbc：$jdbcStr \t username：$username\t password：$password")
    DatabaseFactory.getInstance(jdbcStr, maximumPoolSize.toInt(), username, password, isAutoCommit.toBoolean())

    install(Routing) {
        widget()
        admin()

        //放置静态资源
        static("/static") {
            resources("static")
        }

        //开启404页面提示
        route("{...}") {
            handle {
                call.respondJson(HttpStatusCode.NotFound)
            }
        }
    }
    //通过拦截器打印输出请求元数据
    intercept(ApplicationCallPipeline.Features) {
        logger.attach("req.Id", this.context.request.toLogString()) {
            val cookies = this.context.request.cookies.rawCookies
                    .map { "${it.key}= ${it.value}" }
                    .toString()

            val headers = this.context.request.headers.entries()
                    .map { "${it.key}= ${it.value}" }
                    .toString()

            logger.info("Interceptor[start]${this.context.request.uri}" + "\n\tcookies=$cookies\n\theaders=$headers")
        }

        //利用缓存用户权限列表+url拦截实现权限管理

        proceed()
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start()
}
