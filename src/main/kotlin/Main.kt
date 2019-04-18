import com.fasterxml.jackson.databind.SerializationFeature
import database.DatabaseFactory
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.jackson.jackson
import io.ktor.response.respondJson
import io.ktor.routing.Routing
import io.ktor.routing.route
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.error
import io.ktor.websocket.WebSockets
import web.admin
import web.widget

@KtorExperimentalAPI
fun Application.main() {
//    embeddedServer(Netty, 8080, watchPaths = listOf("MainKt"), module = Application::module).start()

    Main().apply { main() }

}

@KtorExperimentalAPI
class Main {

    val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

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

        //全局异常处理
        install(StatusPages) {
            exception<Throwable> { cause ->
                logger.error(cause)
                call.respondJson(HttpStatusCode(HttpStatusCode.InternalServerError.value, cause.message.toString()))
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
            val sb = StringBuilder("cookies={")
            this.context.request.cookies.rawCookies.forEach { it ->
                sb.append(it.key).append("=").append(it.value).append("&")
            }
            sb.append("}\nheaders={")
            this.context.request.headers.entries().forEach { it ->
                sb.append(it.key).append("=").append(it.value).append("&")
            }
            sb.append("}")

//                sb.append("}\nparams={")
//                this.context.receive<Parameters>().entries().forEach {
//                    sb.append(it.key).append("=").append(it.value).append("&")
//                }
//                sb.append("}")
            logger.info("Interceptor[start]${this.context.request.toLogString()}" +
                    "\n$sb")
            proceed()
        }

    }

//    companion object {
//        @JvmStatic
//        fun main(args: Array<String>) {
//        }
//    }
}