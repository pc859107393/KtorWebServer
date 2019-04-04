import com.fasterxml.jackson.databind.SerializationFeature
import database.DatabaseFactory
import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.toLogString
import io.ktor.http.formUrlEncode
import io.ktor.jackson.jackson
import io.ktor.routing.Routing
import io.ktor.util.KtorExperimentalAPI
import io.ktor.websocket.WebSockets
import log.logger
import web.admin
import web.widget
import java.util.*

@KtorExperimentalAPI
fun Application.main() {
//    embeddedServer(Netty, 8080, watchPaths = listOf("MainKt"), module = Application::module).start()

    Main().apply { main() }

}

@KtorExperimentalAPI
class Main {

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
        log.info("dataBase -> jdbc：$jdbcStr \t username：$username\t password：$password")
        DatabaseFactory.init(jdbcStr, maximumPoolSize.toInt(), username, password, isAutoCommit.toBoolean())

        install(Routing) {
            widget()
            admin()
        }
        //通过拦截器打印输出请求元数据
        intercept(ApplicationCallPipeline.Features) {
            val requestId = UUID.randomUUID()
            logger.attach("req.Id", requestId.toString()) {
                val sb = StringBuilder("cookies={")
                this.context.request.cookies.rawCookies.forEach { it ->
                    sb.append(it.key).append("=").append(it.value).append("&")
                }
                sb.append("}\nheaders={")
                this.context.request.headers.entries().forEach { it ->
                    sb.append(it.key).append("=").append(it.value).append("&")
                }
                sb.append("}")
                logger.info("Interceptor[start]${this.context.request.toLogString()}" +
                        "\nparams:\t${this.context.parameters.formUrlEncode()} \n$sb")
                proceed()
            }
        }

    }
}