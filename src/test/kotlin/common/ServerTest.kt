//package common
//
//import io.ktor.server.engine.ApplicationEngine
//import io.ktor.server.engine.embeddedServer
//import io.ktor.server.netty.Netty
//import io.restassured.RestAssured
//import io.restassured.response.ResponseBodyExtractionOptions
//import io.restassured.specification.RequestSpecification
//import acheng1314.cn.Widgets
//import org.jetbrains.exposed.sql.deleteAll
//import org.jetbrains.exposed.sql.transactions.transaction
//import org.junit.jupiter.api.BeforeAll
//import org.junit.jupiter.api.BeforeEach
//import java.util.concurrent.TimeUnit
//
//open class ServerTest {
//
//    protected fun RequestSpecification.When(): RequestSpecification {
//        return this.`when`()
//    }
//
//    protected inline fun <reified T> ResponseBodyExtractionOptions.to(): T {
//        return this.`as`(T::class.java)
//    }
//
//    companion object {
//
//        private var serverStarted = false
//
//        private lateinit var server: ApplicationEngine
//
//        @BeforeAll
//        @JvmStatic
//        fun startServer() {
//            if(!serverStarted) {
//                server = embeddedServer(Netty, 8080, watchPaths = listOf("Main"), module = )
//                server.start()
//                serverStarted = true
//
//                RestAssured.baseURI = "http://localhost"
//                RestAssured.port = 8080
//                Runtime.getRuntime().addShutdownHook(Thread { server.stop(0, 0, TimeUnit.SECONDS) })
//            }
//        }
//    }
//
//    @BeforeEach
//    fun before() = transaction {
//        Widgets.deleteAll()
//        Unit
//    }
//
//}
