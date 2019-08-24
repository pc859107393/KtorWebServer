package acheng1314.cn.redis

import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import java.lang.Boolean.parseBoolean

class RedissionFactory {

    companion object {
        @Volatile
        private var instance: RedissionFactory? = null

        @Volatile
        private var redissonClient: RedissonClient? = null

        @KtorExperimentalAPI
        @JvmStatic
        fun getInstance(appConfig: ApplicationConfig): RedissionFactory? {
            instance
                    ?: synchronized(RedissionFactory::class.java) {
                instance ?: RedissionFactory().also {
                    val config = Config()
                    var prefix = "redis://"
                    val isSsl: Boolean = parseBoolean(appConfig.property("redission.isSsl").getString())
                    if (isSsl) prefix = "rediss://"
                    val host = appConfig.property("redission.host").getString()
                    val port = appConfig.property("redission.port").getString()
                    val database = appConfig.property("redission.database").getString()
                    val password = appConfig.property("redission.password").getString()
                    config.useSingleServer()
                            .setAddress("$prefix$host:$port")
                            .setDatabase(Integer.parseInt(database)).password = password
                    redissonClient = Redisson.create(config)
                }
            }
            return instance
        }


        fun getRedissionClient() = redissonClient!!

    }

}